package com.davidozzo.agent_service;

import static org.assertj.core.api.Assertions.assertThat;

import com.davidozzo.agent_service.model.TestDocument;
import com.davidozzo.agent_service.repository.TestDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MongoIntegrationTest {

    @Autowired
    private TestDocumentRepository repository;

    @BeforeEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveAndReadDocumentFromMongo() {
        TestDocument saved = repository.save(new TestDocument("ping"));

        assertThat(saved.getId()).isNotBlank();
        assertThat(repository.findById(saved.getId())).isPresent();
        assertThat(repository.findByName("ping")).isPresent();
    }
}
