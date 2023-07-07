package com.tdd.project.TestDrivenDevelopment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tdd.project.TestDrivenDevelopment.Model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
}
