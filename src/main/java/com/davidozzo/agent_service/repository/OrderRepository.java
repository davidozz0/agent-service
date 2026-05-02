package com.davidozzo.agent_service.repository;

import com.davidozzo.agent_service.domain.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerId(String customerId);
}