package com.example.orderservice.application;

import com.example.orderservice.data.DeliveryRepository;
import com.example.orderservice.domain.Delivery;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderStatus;
import com.example.orderservice.domain.Rider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class DeliveryService {

    private final RestTemplate restTemplate;
    private final DeliveryRepository deliveries;

    public DeliveryService(RestTemplate restTemplate, DeliveryRepository deliveries) {
        this.restTemplate = restTemplate;
        this.deliveries = deliveries;
    }

    @Transactional
    public Delivery scheduleDelivery(Order order) {
        List<Rider> riders = List.of(
                restTemplate.getForEntity("http://localhost:8080//deliveries/find-all-riders",
                        Rider[].class).getBody()
        );
        // Dit haalt alle riders uit de database EN alle deliveries, en gaat dan pas kijken wie er ruimte heeft.
        // Dat kan vast handiger!
        Optional<Rider> withLeastDeliveries = riders.stream().min(Comparator.comparingInt(Rider::getNrOfDeliveries));

        if (withLeastDeliveries.isPresent()) {
            Delivery newDelivery = new Delivery(order, withLeastDeliveries.get());
            deliveries.save(newDelivery);
            return newDelivery;
        } else {
            //Dit is natuurlijk fraaier met een custom exception type
            throw new RuntimeException("No available rider found");
        }
    }

    public int getMinutesRemaining(Delivery delivery) {
        if (delivery.getOrder().getStatus() == OrderStatus.Underway) {
            // We gebruiken uiteraard complexe geografische informatie om de resterende tijd te schatten
            // Net als Windows installers!
            return (int) (Math.random() * 100);
        } else {
            return -1;
        }
    }
}