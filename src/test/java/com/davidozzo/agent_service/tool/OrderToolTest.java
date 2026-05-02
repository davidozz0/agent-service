package com.davidozzo.agent_service.tool;

import static org.assertj.core.api.Assertions.assertThat;

import com.davidozzo.agent_service.domain.order.Order;
import com.davidozzo.agent_service.domain.order.OrderItem;
import com.davidozzo.agent_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderToolTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderTool orderTool;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        OrderItem item = OrderItem.builder()
                .productId("prod-1")
                .productName("Laptop")
                .quantity(1)
                .unitPrice(1299.99)
                .subtotal(1299.99)
                .build();

        testOrder = Order.builder()
                .id("order-1")
                .customerId("cust-1")
                .items(List.of(item))
                .totalAmount(1299.99)
                .status("COMPLETED")
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void getAllOrders_shouldReturnAllOrders() {
        List<Order> orders = List.of(testOrder);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderTool.getAllOrders();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("order-1");
    }

    @Test
    void findByCustomerId_shouldReturnOrdersForCustomer() {
        List<Order> orders = List.of(testOrder);
        when(orderRepository.findByCustomerId("cust-1")).thenReturn(orders);

        List<Order> result = orderTool.findByCustomerId("cust-1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerId()).isEqualTo("cust-1");
    }

    @Test
    void findOrderById_shouldReturnOrder_whenExists() {
        when(orderRepository.findById("order-1"))
                .thenReturn(Optional.of(testOrder));

        Order result = orderTool.findOrderById("order-1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("order-1");
    }

    @Test
    void findOrderById_shouldReturnNull_whenNotExists() {
        when(orderRepository.findById("nonexistent"))
                .thenReturn(Optional.empty());

        Order result = orderTool.findOrderById("nonexistent");

        assertThat(result).isNull();
    }
}