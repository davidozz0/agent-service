package com.davidozzo.agent_service.tool;

import static org.assertj.core.api.Assertions.assertThat;

import com.davidozzo.agent_service.domain.product.Product;
import com.davidozzo.agent_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductToolTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductTool productTool;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id("prod-1")
                .name("Laptop Pro")
                .category("Electronics")
                .price(1299.99)
                .stock(50)
                .build();
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        List<Product> products = List.of(testProduct);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productTool.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Laptop Pro");
    }

    @Test
    void findByCategory_shouldReturnProductsInCategory() {
        List<Product> products = List.of(testProduct);
        when(productRepository.findByCategory("Electronics"))
                .thenReturn(products);

        List<Product> result = productTool.findByCategory("Electronics");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo("Electronics");
    }

    @Test
    void findProductById_shouldReturnProduct_whenExists() {
        when(productRepository.findById("prod-1"))
                .thenReturn(Optional.of(testProduct));

        Product result = productTool.findProductById("prod-1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("prod-1");
    }

    @Test
    void findProductById_shouldReturnNull_whenNotExists() {
        when(productRepository.findById("nonexistent"))
                .thenReturn(Optional.empty());

        Product result = productTool.findProductById("nonexistent");

        assertThat(result).isNull();
    }
}