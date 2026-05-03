package com.davidozzo.agent_service;

import com.mongodb.client.MongoClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
@RequiredArgsConstructor
public class TestDatabaseInitializer {

    private final MongoClient mongoClient;

    @PostConstruct
    public void init() {
        try {
            log.info("Dropping all collections in test database before test run...");
            var db = mongoClient.getDatabase("orderintelligence_test");
            db.listCollectionNames().forEach(collectionName -> {
                db.getCollection(collectionName).drop();
                log.info("Dropped collection: {}", collectionName);
            });
            log.info("Test database cleared successfully");
        } catch (Exception e) {
            log.warn("Could not clear database: {}", e.getMessage());
        }
    }
}