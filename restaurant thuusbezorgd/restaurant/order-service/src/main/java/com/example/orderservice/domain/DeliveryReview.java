package com.example.orderservice.domain;

import com.example.orderservice.security.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class DeliveryReview extends Review {

    @ManyToOne
    private Delivery delivery;


    protected DeliveryReview() {
    }

    public DeliveryReview(Delivery delivery, ReviewRating rating, User user) {
        this.delivery = delivery;
        super.rating = rating;
        super.user = user;
    }

    public Delivery getDelivery() {
        return delivery;
    }
}
