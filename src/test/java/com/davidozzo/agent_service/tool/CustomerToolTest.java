package com.davidozzo.agent_service.tool;

import static org.assertj.core.api.Assertions.assertThat;

import com.davidozzo.agent_service.domain.customer.Customer;
import com.davidozzo.agent_service.repository.CustomerRepository;
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
class CustomerToolTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerTool customerTool;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.builder()
                .id("cust-1")
                .name("John Doe")
                .email("john@example.com")
                .churnRiskScore(0.3)
                .build();
    }

    @Test
    void findCustomerByEmail_shouldReturnCustomer_whenExists() {
        when(customerRepository.findByEmail("john@example.com"))
                .thenReturn(Optional.of(testCustomer));

        Customer result = customerTool.findCustomerByEmail("john@example.com");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void findCustomerByEmail_shouldReturnNull_whenNotExists() {
        when(customerRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        Customer result = customerTool.findCustomerByEmail("unknown@example.com");

        assertThat(result).isNull();
    }

    @Test
    void getAllCustomers_shouldReturnAllCustomers() {
        List<Customer> customers = List.of(testCustomer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerTool.getAllCustomers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    void findCustomerById_shouldReturnCustomer_whenExists() {
        when(customerRepository.findById("cust-1"))
                .thenReturn(Optional.of(testCustomer));

        Customer result = customerTool.findCustomerById("cust-1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("cust-1");
    }

    @Test
    void findCustomerById_shouldReturnNull_whenNotExists() {
        when(customerRepository.findById("nonexistent"))
                .thenReturn(Optional.empty());

        Customer result = customerTool.findCustomerById("nonexistent");

        assertThat(result).isNull();
    }
}