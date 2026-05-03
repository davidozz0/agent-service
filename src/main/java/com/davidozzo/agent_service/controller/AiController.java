package com.davidozzo.agent_service.controller;

import com.davidozzo.agent_service.domain.audit.ChatSession;
import com.davidozzo.agent_service.repository.ChatSessionRepository;
import com.davidozzo.agent_service.tool.CustomerTool;
import com.davidozzo.agent_service.tool.OrderTool;
import com.davidozzo.agent_service.tool.ProductTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ai")
@ConditionalOnBean(ChatClient.class)
public class AiController {

    private final ChatClient chatClient;
    private final CustomerTool customerTool;
    private final ProductTool productTool;
    private final OrderTool orderTool;
    private final ChatSessionRepository chatSessionRepository;

    public AiController(ChatClient chatClient,
                        CustomerTool customerTool,
                        ProductTool productTool,
                        OrderTool orderTool,
                        ChatSessionRepository chatSessionRepository) {
        this.chatClient = chatClient;
        this.customerTool = customerTool;
        this.productTool = productTool;
        this.orderTool = orderTool;
        this.chatSessionRepository = chatSessionRepository;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        log.info("Chat request received: {}", request.getMessage());

        String sessionId = request.getSessionId() != null
                ? request.getSessionId()
                : UUID.randomUUID().toString();

        List<ChatSession> previousSessions = chatSessionRepository.findBySessionIdOrderByCreatedAtDesc(sessionId);
        String conversationHistory = buildConversationHistory(previousSessions);

        String systemPrompt = """
            You are an assistant for an order management system.
            You can use the available tools to look up customers, products, and orders.
            If the user asks about customers, use the customer tools.
            If the user asks about products, use the product tools.
            Always provide clear and concise answers.
            Reply in the same language of the request.

            Previous conversation:
            %s
            """.formatted(conversationHistory.isEmpty() ? "No previous messages" : conversationHistory);

        String response = chatClient.prompt()
                .system(systemPrompt)
                .user(request.getMessage())
                .tools(customerTool, productTool, orderTool)
                .call()
                .content();

        log.info("Chat response sent: {}", response);

        saveAudit(sessionId, request.getMessage(), response);

        return response;
    }

    private String buildConversationHistory(List<ChatSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (ChatSession session : sessions) {
            sb.append("User: ").append(session.getUserPrompt()).append("\n");
            sb.append("Assistant: ").append(session.getAiResponse()).append("\n");
        }
        return sb.toString();
    }

    private void saveAudit(String sessionId, String userPrompt, String aiResponse) {
        try {
            ChatSession session = ChatSession.builder()
                    .sessionId(sessionId)
                    .createdAt(Instant.now())
                    .userPrompt(userPrompt)
                    .aiResponse(aiResponse)
                    .build();

            chatSessionRepository.save(session);
            log.debug("Audit saved for session: {}", sessionId);
        } catch (Exception e) {
            log.error("Failed to save audit", e);
        }
    }

    public static class ChatRequest {
        private String message;
        private String sessionId;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }
}