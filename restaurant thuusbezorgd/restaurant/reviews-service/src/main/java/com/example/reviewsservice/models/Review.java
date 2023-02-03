package com.example.reviewsservice.models;

import com.example.reviewsservice.security.User;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;

@Entity
@Inheritance
public abstract class Review {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    protected ReviewRating rating;

    @ManyToOne
    protected User user;

    protected Review(){}

    public long getId() {
        return id;
    }

    public ReviewRating getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }
}
