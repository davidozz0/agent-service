package com.davidozzo.agent_service.tool;

import com.davidozzo.agent_service.domain.product.Product;
import com.davidozzo.agent_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductTool {

    private final ProductRepository productRepository;

    @Tool(name = "get_all_products", description = "Get all products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Tool(name = "find_products_by_category", description = "Find products by category")
    public List<Product> findByCategory(
            @ToolParam(description = "The category to filter by") String category) {
        return productRepository.findByCategory(category);
    }

    @Tool(name = "find_product_by_id", description = "Find a product by ID")
    public Product findProductById(
            @ToolParam(description = "The ID of the product") String id) {
        return productRepository.findById(id).orElse(null);
    }
}