package com.example.reviewsservice.models;

import com.example.reviewsservice.security.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class DishReview extends Review {

    @ManyToOne
    private Dish dish;

    protected DishReview(){}

    public DishReview(Dish dish, ReviewRating rating, User user) {
        this.dish = dish;
        super.rating = rating;
        super.user = user;
    }

    public Dish getDish() {
        return dish;
    }
}
