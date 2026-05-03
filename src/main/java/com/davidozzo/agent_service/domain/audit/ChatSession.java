package com.davidozzo.agent_service.domain.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_sessions")
public class ChatSession {

    @Id
    private String id;

    private String sessionId;

    private Instant createdAt;

    private String userPrompt;

    private String aiResponse;

    private List<ToolCallRecord> toolCalls;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToolCallRecord {
        private String toolName;
        private String methodName;
        private Object parameters;
        private Object result;
        private Instant calledAt;
    }
}