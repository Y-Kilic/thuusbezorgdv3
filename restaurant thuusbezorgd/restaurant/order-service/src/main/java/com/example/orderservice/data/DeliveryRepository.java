package com.example.orderservice.data;

import com.example.orderservice.domain.Delivery;
import com.example.orderservice.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Query(name = "Delivery.findRandom")
    Optional<Delivery> findRandomDelivery();

    List<Delivery> findByOrder_User(User user);
}
