package com.davidozzo.agent_service.repository;

import java.util.Optional;

import com.davidozzo.agent_service.model.TestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestDocumentRepository extends MongoRepository<TestDocument, String> {
    Optional<TestDocument> findByName(String name);
}