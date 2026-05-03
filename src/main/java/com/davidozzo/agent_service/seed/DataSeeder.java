package com.davidozzo.agent_service.seed;

import com.davidozzo.agent_service.domain.customer.Customer;
import com.davidozzo.agent_service.domain.order.Order;
import com.davidozzo.agent_service.domain.order.OrderItem;
import com.davidozzo.agent_service.domain.product.Product;
import com.davidozzo.agent_service.repository.CustomerRepository;
import com.davidozzo.agent_service.repository.OrderRepository;
import com.davidozzo.agent_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    @Override
    public void run(String... args) {
        if (customerRepo.count() > 0) {
            log.info("Data already seeded, skipping");
            return;
        }

        log.info("Seeding database...");

        List<Customer> customers = customerRepo.saveAll(List.of(
                Customer.builder().name("Alice Johnson").email("alice@example.com").churnRiskScore(0.15).build(),
                Customer.builder().name("Bob Smith").email("bob@example.com").churnRiskScore(0.65).build(),
                Customer.builder().name("Carol White").email("carol@example.com").churnRiskScore(0.30).build(),
                Customer.builder().name("David Brown").email("david@example.com").churnRiskScore(0.80).build(),
                Customer.builder().name("Eve Davis").email("eve@example.com").churnRiskScore(0.10).build()
        ));

        List<Product> products = productRepo.saveAll(List.of(
                Product.builder().name("Laptop Pro 15").category("Electronics").price(1299.99).stock(50).build(),
                Product.builder().name("Wireless Mouse").category("Electronics").price(29.99).stock(200).build(),
                Product.builder().name("USB-C Hub").category("Electronics").price(49.99).stock(150).build(),
                Product.builder().name("Mechanical Keyboard").category("Electronics").price(149.99).stock(75).build(),
                Product.builder().name("Monitor 27 inch").category("Electronics").price(399.99).stock(30).build(),
                Product.builder().name("Office Chair").category("Furniture").price(299.99).stock(25).build(),
                Product.builder().name("Standing Desk").category("Furniture").price(599.99).stock(15).build(),
                Product.builder().name("Desk Lamp").category("Furniture").price(45.99).stock(80).build(),
                Product.builder().name("Notebook Pack").category("Office").price(12.99).stock(500).build(),
                Product.builder().name("Pen Set").category("Office").price(8.99).stock(300).build()
        ));

        List<Order> orders = orderRepo.saveAll(List.of(
                buildOrder(customers.get(0), products.get(0), products.get(1), Instant.now().minus(30, ChronoUnit.DAYS)),
                buildOrder(customers.get(0), products.get(2), Instant.now().minus(15, ChronoUnit.DAYS)),
                buildOrder(customers.get(1), products.get(5), Instant.now().minus(60, ChronoUnit.DAYS)),
                buildOrder(customers.get(1), products.get(6), Instant.now().minus(5, ChronoUnit.DAYS)),
                buildOrder(customers.get(2), products.get(3), products.get(4), Instant.now().minus(10, ChronoUnit.DAYS)),
                buildOrder(customers.get(3), products.get(0), Instant.now().minus(2, ChronoUnit.DAYS)),
                buildOrder(customers.get(4), products.get(8), products.get(9), Instant.now().minus(1, ChronoUnit.DAYS))
        ));

        log.info("Seeded {} customers, {} products, {} orders",
                customers.size(), products.size(), orders.size());
    }

    private Order buildOrder(Customer customer, Product p1, Product p2, Instant date) {
        Order order = Order.builder()
                .customerId(customer.getId())
                .items(List.of(
                        OrderItem.builder()
                                .productId(p1.getId())
                                .productName(p1.getName())
                                .quantity(1)
                                .unitPrice(p1.getPrice())
                                .subtotal(p1.getPrice())
                                .build(),
                        OrderItem.builder()
                                .productId(p2.getId())
                                .productName(p2.getName())
                                .quantity(1)
                                .unitPrice(p2.getPrice())
                                .subtotal(p2.getPrice())
                                .build()
                ))
                .status("COMPLETED")
                .createdAt(date)
                .build();
        order.setTotalAmount(order.getItems().stream().mapToDouble(OrderItem::getSubtotal).sum());
        return order;
    }

    private Order buildOrder(Customer customer, Product p1, Instant date) {
        Order order = Order.builder()
                .customerId(customer.getId())
                .items(List.of(
                        OrderItem.builder()
                                .productId(p1.getId())
                                .productName(p1.getName())
                                .quantity(1)
                                .unitPrice(p1.getPrice())
                                .subtotal(p1.getPrice())
                                .build()
                ))
                .status("COMPLETED")
                .createdAt(date)
                .build();
        order.setTotalAmount(order.getItems().stream().mapToDouble(OrderItem::getSubtotal).sum());
        return order;
    }
}