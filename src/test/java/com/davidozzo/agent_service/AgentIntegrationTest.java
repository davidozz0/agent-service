package com.davidozzo.agent_service;

import com.davidozzo.agent_service.controller.AiController;
import com.davidozzo.agent_service.domain.audit.ChatSession;
import com.davidozzo.agent_service.repository.ChatSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AgentIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private AiController aiController;

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    @BeforeEach
    void cleanUp() {
        chatSessionRepository.deleteAll();
    }

    @Test
    void shouldSaveAuditToMongoDB_whenChatting() {
        AiController.ChatRequest request = new AiController.ChatRequest();
        request.setMessage("Quali clienti avete?");

        aiController.chat(request);

        List<ChatSession> sessions = chatSessionRepository.findAll();
        assertThat(sessions).hasSize(1);
        assertThat(sessions.get(0).getUserPrompt()).isEqualTo("Quali clienti avete?");
    }

    @Test
    void shouldRetrieveConversationHistory_withSameSessionId() {
        String sessionId = "test-session-123";

        AiController.ChatRequest firstRequest = new AiController.ChatRequest();
        firstRequest.setMessage("Quali prodotti vendete?");
        firstRequest.setSessionId(sessionId);

        AiController.ChatRequest secondRequest = new AiController.ChatRequest();
        secondRequest.setMessage("Qual è il prodotto più costoso?");
        secondRequest.setSessionId(sessionId);

        aiController.chat(firstRequest);
        aiController.chat(secondRequest);

        List<ChatSession> sessions = chatSessionRepository.findBySessionIdOrderByCreatedAtDesc(sessionId);
        assertThat(sessions).hasSize(2);
    }

    @Test
    void shouldReturnResponse_whenAskingAboutCustomers() {
        AiController.ChatRequest request = new AiController.ChatRequest();
        request.setMessage("Quanti clienti ci sono?");

        String response = aiController.chat(request);

        assertThat(response).isNotBlank();
    }

    @Test
    void shouldReturnResponse_whenAskingAboutProducts() {
        AiController.ChatRequest request = new AiController.ChatRequest();
        request.setMessage("Quali categorie di prodotti sono disponibili?");

        String response = aiController.chat(request);

        assertThat(response).isNotBlank();
    }

    @Test
    void shouldReturnResponse_whenAskingAboutOrders() {
        AiController.ChatRequest request = new AiController.ChatRequest();
        request.setMessage("Quanti ordini sono stati effettuati?");

        String response = aiController.chat(request);

        assertThat(response).isNotBlank();
    }

    @Test
    void shouldGenerateNewSessionId_whenNotProvided() {
        AiController.ChatRequest request = new AiController.ChatRequest();
        request.setMessage("Ciao");

        String response = aiController.chat(request);

        assertThat(response).isNotBlank();

        List<ChatSession> sessions = chatSessionRepository.findAll();
        assertThat(sessions).hasSize(1);
        assertThat(sessions.get(0).getSessionId()).isNotBlank();
    }
}