package com.example.dishservice.data;

import com.example.dishservice.domain.Delivery;
import com.example.dishservice.domain.DeliveryReview;
import com.example.dishservice.domain.Dish;
import com.example.dishservice.domain.DishReview;
import com.example.dishservice.domain.Review;
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
