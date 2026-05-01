package com.davidozzo.agent_service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class OllamaIntegrationTest {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Test
    void shouldCallOllamaSuccessfully() {
        String response = chatClientBuilder.build()
                .prompt()
                .user("Rispondi solo con la parola: pong")
                .call()
                .content();

        assertThat(response).isNotBlank();
        assertThat(response.toLowerCase()).contains("pong");
    }
}
