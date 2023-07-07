package com.tdd.project.TestDrivenDevelopment;

import com.tdd.project.TestDrivenDevelopment.Exception.ErrorResponse;
import com.tdd.project.TestDrivenDevelopment.Model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateOrder_ValidFields_ReturnsCreatedOrder() {
        // Create a valid order
        Order order = new Order();
        order.setCustomerName("John Taylor");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("333 King St");
        order.setTotal(300.0);

        // Perform the creation of the order
        ResponseEntity<Order> response = restTemplate.postForEntity("/orders", order, Order.class);

        // Assert that the response status code is 201 (Created)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Assert that the created order matches the input order
        Order createdOrder = response.getBody();
        assertEquals(order.getCustomerName(), createdOrder.getCustomerName());
        assertEquals(order.getOrderDate(), createdOrder.getOrderDate());
        assertEquals(order.getShippingAddress(), createdOrder.getShippingAddress());
        assertEquals(order.getTotal(), createdOrder.getTotal());
    }

    @Test
    public void testCreateOrder_MissingCustomerName_ThrowsConstraintViolationException() {
        // Create an order with missing customer name
        Order order = new Order();
        order.setCustomerName("");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("333 King St");
        order.setTotal(300.0);

        // Perform the creation of the order
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity("/orders", order, ErrorResponse.class);

        // Assert that the response status code is 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Assert that the response body contains the expected validation error message
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Validation Error", errorResponse.getMessage());
        assertTrue(errorResponse.getErrors().contains("customerName: Customer name required"));
    }

    @Test
    public void testCreateOrder_MissingOrderDate_ThrowsConstraintViolationException() {
        // Create an order with missing order date
        Order order = new Order();
        order.setCustomerName("John Taylor");
        order.setShippingAddress("333 King St");
        order.setTotal(300.0);

        // Perform the creation of the order
        ResponseEntity<Order> response = restTemplate.postForEntity("/orders", order, Order.class);

        // Assert that the response status code is 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateOrder_MissingShippingAddress_ThrowsConstraintViolationException() {
        // Create an order with missing shipping address
        Order order = new Order();
        order.setCustomerName("John Taylor");
        order.setOrderDate(LocalDate.now());
        order.setTotal(300.0);

        // Perform the creation of the order
        ResponseEntity<Order> response = restTemplate.postForEntity("/orders", order, Order.class);

        // Assert that the response status code is 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateOrder_NegativeTotal_ThrowsConstraintViolationException() {
        // Create an order with negative total
        Order order = new Order();
        order.setCustomerName("John Taylor");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("333 King St");
        order.setTotal(-300.0);

        // Perform the creation of the order
        ResponseEntity<Order> response = restTemplate.postForEntity("/orders", order, Order.class);

        // Assert that the response status code is 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteOrder_NonExistentOrder_ThrowsException() {
        // Perform the deletion of a non-existent order with ID -1
        ResponseEntity<Void> response = restTemplate.exchange("/orders/-1", HttpMethod.DELETE, null, Void.class);

        // Assert that the response status code is 404 (Not Found)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}