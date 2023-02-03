package com.example.orderservice.data;

import com.example.orderservice.domain.Delivery;
import com.example.orderservice.domain.DeliveryReview;
import com.example.orderservice.domain.Dish;
import com.example.orderservice.domain.DishReview;
import com.example.orderservice.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from DishReview r where r.dish = :dish")
    List<DishReview> findDishReviews(@Param("dish") Dish dish);


    @Query("select r from DeliveryReview r where r.delivery = :delivery")
    List<DeliveryReview> findDeliveryReviews(@Param("delivery") Delivery delivery);
}
