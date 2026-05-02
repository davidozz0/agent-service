package com.davidozzo.agent_service;

import static org.assertj.core.api.Assertions.assertThat;

import com.davidozzo.agent_service.domain.customer.Customer;
import com.davidozzo.agent_service.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MongoIntegrationTest {

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveAndReadCustomerFromMongo() {
        Customer saved = repository.save(Customer.builder()
                .name("John Doe")
                .email("john@example.com")
                .churnRiskScore(0.2)
                .build());

        assertThat(saved.getId()).isNotBlank();
        assertThat(repository.findById(saved.getId())).isPresent();
        assertThat(repository.findByEmail("john@example.com")).isPresent();
    }
}