package com.example.orderservice.application;

import com.example.orderservice.data.DeliveryRepository;
import com.example.orderservice.domain.Delivery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class RandomDeliveryTask {
    private final DeliveryRepository deliveries;

    public RandomDeliveryTask(DeliveryRepository deliveries) {
        this.deliveries = deliveries;
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void pretendActualRealWorldStuff() {
        Optional<Delivery> delivery = this.deliveries.findRandomDelivery();

        if (delivery.isPresent()) {
            Delivery someDelivery = delivery.get();
            someDelivery.getOrder().setStatus(someDelivery.getOrder().getStatus().next());
        }
    }
}
