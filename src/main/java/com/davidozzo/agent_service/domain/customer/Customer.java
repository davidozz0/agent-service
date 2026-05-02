package com.davidozzo.agent_service.domain.customer;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    @Builder.Default
    private Instant createdAt = Instant.now();
    private Double churnRiskScore;
}