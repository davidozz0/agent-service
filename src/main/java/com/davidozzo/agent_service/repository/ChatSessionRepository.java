package com.davidozzo.agent_service.repository;

import com.davidozzo.agent_service.domain.audit.ChatSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatSessionRepository extends MongoRepository<ChatSession, String> {

    List<ChatSession> findBySessionIdOrderByCreatedAtDesc(String sessionId);

    List<ChatSession> findTop10ByOrderByCreatedAtDesc();
}