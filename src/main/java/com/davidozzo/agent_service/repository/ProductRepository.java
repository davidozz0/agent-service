package com.davidozzo.agent_service.repository;

import com.davidozzo.agent_service.domain.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(String category);
}