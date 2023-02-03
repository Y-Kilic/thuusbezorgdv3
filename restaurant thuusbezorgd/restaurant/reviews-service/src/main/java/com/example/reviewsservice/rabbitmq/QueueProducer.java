package com.example.reviewsservice.rabbitmq;

import com.example.reviewsservice.models.DeliveryReview;
import com.example.reviewsservice.models.DishReview;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {
    private String fanoutExchange = "myEchange";

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public QueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void produceDishReviews(DishReview review) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchange);
        rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(review));
    }
    public void produceDeliveryReviews(DeliveryReview review) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchange);
        rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(review));
    }
}