package com.davidozzo.agent_service.controller;

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

@Slf4j
@RestController
@RequestMapping("/api/ai")
@ConditionalOnBean(ChatClient.class)
public class AiController {

    private final ChatClient chatClient;
    private final CustomerTool customerTool;
    private final ProductTool productTool;
    private final OrderTool orderTool;

    public AiController(ChatClient chatClient, CustomerTool customerTool, ProductTool productTool, OrderTool orderTool) {
        this.chatClient = chatClient;
        this.customerTool = customerTool;
        this.productTool = productTool;
        this.orderTool = orderTool;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        log.info("Chat request received: {}", request.getMessage());

        String systemPrompt = """
            You are an assistant for an order management system.
            You can use the available tools to look up customers, products, and orders.
            If the user asks about customers, use the customer tools.
            If the user asks about products, use the product tools.
            Always provide clear and concise answers.
            Reply in the same language of the request.
            """;

        String response = chatClient.prompt()
                .system(systemPrompt)
                .user(request.getMessage())
                .tools(customerTool, productTool, orderTool)
                .call()
                .content();

        log.info("Chat response sent: {}", response);

        return response;
    }

    public static class ChatRequest {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}