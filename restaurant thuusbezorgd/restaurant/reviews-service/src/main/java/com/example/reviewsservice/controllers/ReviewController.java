package com.example.reviewsservice.controllers;

import com.example.reviewsservice.rabbitmq.QueueConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ReviewController {
    private final QueueConsumer queueConsumer;
    public ReviewController(QueueConsumer queueConsumer) {
        this.queueConsumer = queueConsumer;
    }
//    @PostMapping("/produce-dish-reviews")
//    public ResponseEntity<?> storeDishReviews(DishReview dishReview) throws JsonProcessingException {
//        queueProducer.produceDishReviews(dishReview);
//        return new ResponseEntity<DishReview>(HttpStatus.CREATED);
//    }
//    @PostMapping("produce-delivery-reviews")
//    public ResponseEntity<?> storeDeliveryReviews(DeliveryReview deliveryReview) throws JsonProcessingException {
//        queueProducer.produceDeliveryReviews(deliveryReview);
//        return new ResponseEntity<DeliveryReview>(HttpStatus.CREATED);
//    }

    @GetMapping("/consume-delivery-reviews")
    public ResponseEntity<?> getDeliveryReviews() throws JsonProcessingException {
//        DeliveryReview deliveryReview = queueConsumer.processMessage();
        return new ResponseEntity<>(queueConsumer.processMessage(), HttpStatus.OK);
    }
}