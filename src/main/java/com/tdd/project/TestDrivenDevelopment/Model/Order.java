package com.tdd.project.TestDrivenDevelopment.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
@Table(name="orders")
public class Order{
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty(message = "Customer name required")
    @Column(nullable = false)
    private String customerName;
    @NotNull(message = "Order date required")
    @Column(nullable = false)
    private LocalDate orderDate;
    @NotBlank(message = "Shipping address required")
    @Column(nullable = false)
    private String shippingAddress;
    @NotNull(message = "Total required")
    @Positive(message = "Total must be positive")
    @Column(nullable = false)
    private Double total;

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
