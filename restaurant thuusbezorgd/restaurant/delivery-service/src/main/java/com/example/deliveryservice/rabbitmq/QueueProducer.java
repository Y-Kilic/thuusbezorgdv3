package com.example.deliveryservice.rabbitmq;

import com.example.deliveryservice.domain.DishReview;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.example.deliveryservice.data.ReviewRepository;
import com.example.deliveryservice.domain.DeliveryReview;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {
    private String fanoutExchange = "myExchange";
    private final RabbitTemplate rabbitTemplate;
    private final ReviewRepository reviewRepository;

    @Autowired
    public QueueProducer(RabbitTemplate rabbitTemplate, ReviewRepository reviewRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.reviewRepository = reviewRepository;
    }
    public void produceDishReviews(DishReview review) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchange);
        rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(review));
    }
    public void produceDeliveryReviews(DeliveryReview deliveryReview) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        rabbitTemplate.setExchange(fanoutExchange);
        rabbitTemplate.convertAndSend(objectMapper.writeValueAsString(deliveryReview));
        reviewRepository.save(deliveryReview);
    }
}