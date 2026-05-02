package com.davidozzo.agent_service.tool;

import com.davidozzo.agent_service.domain.customer.Customer;
import com.davidozzo.agent_service.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerTool {

    private final CustomerRepository customerRepository;

    @Tool(name = "find_customer_by_email", description = "Find a customer by email address")
    public Customer findCustomerByEmail(
            @ToolParam(description = "The email address of the customer") String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        return customer.orElse(null);
    }

    @Tool(name = "get_all_customers", description = "Get all customers")
    public java.util.List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Tool(name = "find_customer_by_id", description = "Find a customer by ID")
    public Customer findCustomerById(
            @ToolParam(description = "The ID of the customer") String id) {
        return customerRepository.findById(id).orElse(null);
    }
}