package com.example.reviewsservice.rabbitmq;

import com.example.reviewsservice.models.DeliveryReview;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    private final RabbitTemplate rabbitTemplate;

    private String queueName = "myQueue";
    @Autowired
    public QueueConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    private String receiveMessage() {
        String message = (String) rabbitTemplate.receiveAndConvert(queueName);
        return message;
    }

    public Object processMessage() throws JsonProcessingException {
        String message = receiveMessage();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(message, Object.class);
    }
}