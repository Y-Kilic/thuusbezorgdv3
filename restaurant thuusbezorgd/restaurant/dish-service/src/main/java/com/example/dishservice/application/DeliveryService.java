package com.example.dishservice.application;

import com.example.dishservice.domain.Delivery;
import com.example.dishservice.domain.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class DeliveryService {

//    private final RiderRepository riders;
//    private final DeliveryRepository deliveries;
//
//    public DeliveryService(RiderRepository riders, DeliveryRepository deliveries) {
//        this.riders = riders;
//        this.deliveries = deliveries;
//    }

//    @Transactional
//    public Delivery scheduleDelivery(Order order) {
//        List<Rider> riders = this.riders.findAll();
//        // Dit haalt alle riders uit de database EN alle deliveries, en gaat dan pas kijken wie er ruimte heeft.
//        // Dat kan vast handiger!
//        Optional<Rider> withLeastDeliveries = riders.stream().min(Comparator.comparingInt(Rider::getNrOfDeliveries));
//
//        if (withLeastDeliveries.isPresent()) {
//            Delivery newDelivery = new Delivery(order, withLeastDeliveries.get());
//            deliveries.save(newDelivery);
//            return newDelivery;
//        } else {
//            //Dit is natuurlijk fraaier met een custom exception type
//            throw new RuntimeException("No available rider found");
//        }
//    }

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