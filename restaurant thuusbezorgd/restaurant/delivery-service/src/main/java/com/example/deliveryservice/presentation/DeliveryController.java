package com.example.deliveryservice.presentation;

import com.example.deliveryservice.data.DeliveryRepository;
import com.example.deliveryservice.data.ReviewRepository;
import com.example.deliveryservice.data.RiderRepository;
import com.example.deliveryservice.domain.Delivery;
import com.example.deliveryservice.domain.DeliveryReview;
import com.example.deliveryservice.domain.ReviewRating;
import com.example.deliveryservice.domain.Rider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.deliveryservice.application.DeliveryService;

import com.example.deliveryservice.rabbitmq.QueueProducer;
import com.example.deliveryservice.security.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final DeliveryRepository deliveries;
    private final ReviewRepository reviews;
    private final RiderRepository riderRepository;
    private final QueueProducer queueProducer;

    public DeliveryController(DeliveryService deliveryService, DeliveryRepository deliveries, ReviewRepository reviews, RiderRepository riderRepository, QueueProducer queueProducer) {
        this.deliveryService = deliveryService;
        this.deliveries = deliveries;
        this.reviews = reviews;
        this.riderRepository = riderRepository;
        this.queueProducer = queueProducer;
    }

    public record DeliveryResponseDTO(long id, String riderName, int minutesRemaining, String orderLink) {
        public static DeliveryResponseDTO fromDelivery(Delivery d, int minutes) {
            return new DeliveryResponseDTO(d.getId(), d.getRider().getName(), minutes, "/orders/" + d.getOrder().getId());
        }
    }

    @GetMapping
    public List<DeliveryResponseDTO> deliveries(User user) {
        List<Delivery> found = deliveries.findByOrder_User(user);

        return found.stream().map(d -> DeliveryResponseDTO.fromDelivery(d, this.deliveryService.getMinutesRemaining(d))).toList();
    }

    @GetMapping("{id}")
    public ResponseEntity<DeliveryResponseDTO> getDelivery(User user, @PathVariable long id) {
        Optional<Delivery> delivery = this.deliveries.findById(id);

        if (delivery.isEmpty() || delivery.get().getOrder().getUser() != user) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                DeliveryResponseDTO.fromDelivery(delivery.get(), this.deliveryService.getMinutesRemaining(delivery.get())));
    }


    public record ReviewDTO(String delivery, int rating) {
        public static ReviewDTO fromReview(DeliveryReview review) {
            return new ReviewDTO(review.getDelivery().getId().toString(), review.getRating().toInt());
        }
    }

    public record PostedReviewDTO(int rating) {

    }


    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getDishReviews(@PathVariable("id") long id) {
        Optional<Delivery> d = this.deliveries.findById(id);
        if (d.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<DeliveryReview> reviews = this.reviews.findDeliveryReviews(d.get());
        return ResponseEntity.ok(reviews.stream().map(ReviewDTO::fromReview).toList());
    }

    @PostMapping("/{id}/reviews")
    @Transactional
    public ResponseEntity<?> postReview(
            User user,
            @PathVariable("id") long id,
            @RequestBody PostedReviewDTO reviewDTO) throws JsonProcessingException {
        Optional<Delivery> found = this.deliveries.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>("Delivery Id Not Found", HttpStatus.NOT_FOUND);
        }

        DeliveryReview review = new DeliveryReview(found.get(),
                ReviewRating.fromInt(reviewDTO.rating()));
        queueProducer.produceDeliveryReviews(review);
        return ResponseEntity.ok(ReviewDTO.fromReview(review));
    }

    @GetMapping("/find-all-riders")
    public ResponseEntity<List<Rider>> findAllRiders() {
        return ResponseEntity.ok(riderRepository.findAll());
    }
}