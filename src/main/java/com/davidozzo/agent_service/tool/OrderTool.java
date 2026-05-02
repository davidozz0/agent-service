package com.davidozzo.agent_service.tool;

import com.davidozzo.agent_service.domain.order.Order;
import com.davidozzo.agent_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderTool {

    private final OrderRepository orderRepository;

    @Tool(name = "get_all_orders", description = "Get all orders")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Tool(name = "find_orders_by_customer", description = "Find orders by customer ID")
    public List<Order> findByCustomerId(
            @ToolParam(description = "The customer ID to filter orders") String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Tool(name = "find_order_by_id", description = "Find an order by ID")
    public Order findOrderById(
            @ToolParam(description = "The ID of the order") String id) {
        return orderRepository.findById(id).orElse(null);
    }
}