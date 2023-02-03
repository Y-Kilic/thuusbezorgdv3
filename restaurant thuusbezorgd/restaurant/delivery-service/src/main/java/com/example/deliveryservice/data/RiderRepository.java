package com.example.deliveryservice.data;

import com.example.deliveryservice.domain.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long> {
}
