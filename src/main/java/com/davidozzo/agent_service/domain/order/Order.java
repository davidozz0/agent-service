package com.davidozzo.agent_service.domain.order;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String customerId;
    private List<OrderItem> items;
    private Double totalAmount;
    @Builder.Default
    private String status = "PENDING";
    @Builder.Default
    private Instant createdAt = Instant.now();
}