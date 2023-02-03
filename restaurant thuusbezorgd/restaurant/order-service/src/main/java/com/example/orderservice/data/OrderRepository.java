package com.example.orderservice.data;

import com.example.orderservice.domain.Order;
import com.example.orderservice.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User currentUser);
}
