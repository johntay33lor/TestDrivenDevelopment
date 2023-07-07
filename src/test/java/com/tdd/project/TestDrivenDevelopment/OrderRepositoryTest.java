package com.tdd.project.TestDrivenDevelopment;

import com.tdd.project.TestDrivenDevelopment.Model.Order;
import com.tdd.project.TestDrivenDevelopment.Repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderRepositoryTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder () {
        //Order object
        Order order = new Order();
        order.setCustomerName("John Taylor");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("333 King St");
        order.setTotal(300.0);

        //Save order
        Order orderSaved = orderRepository.save(order);

        //Make sure none of the fields are null
        assertNotNull(orderSaved.getCustomerName());
        assertEquals(order.getCustomerName(), orderSaved.getCustomerName());
        assertNotNull(orderSaved.getOrderDate());
        assertEquals(order.getOrderDate(), orderSaved.getOrderDate());
        assertNotNull(orderSaved.getShippingAddress());
        assertEquals(order.getShippingAddress(), orderSaved.getShippingAddress());
        assertNotNull(orderSaved.getTotal());
        assertEquals(order.getTotal(), orderSaved.getTotal());

    }

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setCustomerName("John Taylor");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("333 King St");
        order.setTotal(300.0);

        // Perform the creation of the order
        ResponseEntity<Order> response = restTemplate.postForEntity("/orders", order, Order.class);

        // Assert that the response status code is 201 (Created)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Assert that the response body and its attributes are not null
        assertNotNull(response.getBody());
        Order createdOrder = response.getBody();
        assertNotNull(createdOrder.getId());
        assertEquals("John Taylor", createdOrder.getCustomerName());
        assertEquals(LocalDate.now(), createdOrder.getOrderDate());
        assertEquals("333 King St", createdOrder.getShippingAddress());
        assertEquals(300.0, createdOrder.getTotal());
    }

    @Test
    public void testReadOrder() {
        // Non-existing order ID
        Long orderId = 1000L;

        // Perform the request to fetch the order
        ResponseEntity<String> response = restTemplate.getForEntity("/orders/{orderId}", String.class, orderId);

        // Assert that the response status code is 404 (Not Found)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Assert that the response body contains the expected error message
        String errorMessage = "Order not found with id: " + orderId;
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    public void testUpdateOrder() {
        // Create an order with valid fields
        Order order = new Order();
        order.setCustomerName("John Taylor");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("333 King St");
        order.setTotal(300.0);

        // Save the order to get an ID
        Order savedOrder = orderRepository.save(order);

        // Update the order details
        Order orderUpdated = new Order();
        orderUpdated.setId(savedOrder.getId());
        orderUpdated.setCustomerName("Tempestt Taylor");
        orderUpdated.setOrderDate(LocalDate.now());
        orderUpdated.setShippingAddress("123 Main St");
        orderUpdated.setTotal(33.0);

        // Perform the update
        ResponseEntity<Order> response = restTemplate.exchange(
                "/orders/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(orderUpdated),
                Order.class,
                savedOrder.getId()
        );

        // Assert that the response status code is 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the updated order matches the input order
        Order updatedOrder = response.getBody();
        assertNotNull(updatedOrder);
        assertEquals(orderUpdated.getId(), updatedOrder.getId());
        assertEquals(orderUpdated.getCustomerName(), updatedOrder.getCustomerName());
        assertEquals(orderUpdated.getOrderDate(), updatedOrder.getOrderDate());
        assertEquals(orderUpdated.getShippingAddress(), updatedOrder.getShippingAddress());
        assertEquals(orderUpdated.getTotal(), updatedOrder.getTotal());
    }

    @Test
    public void testDeleteOrder() {
        // Non-existing order ID
        Long orderId = 1000L;

        // Perform the deletion
        ResponseEntity<Void> response = restTemplate.exchange(
                "/orders/{orderId}",
                HttpMethod.DELETE,
                null,
                Void.class,
                orderId
        );

        // Assert that the response status code is 404 (Not Found)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
