package com.example.deliveryservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.deliveryservice.security.User;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders") //Order is een keyword in sql, so this works around some wonky sql-generator implementations
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime orderDate;

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    @OneToOne
    @JsonIgnoreProperties("order")
    private Delivery delivery;

    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    @OneToMany(mappedBy = "id.order")
    @Cascade(CascadeType.PERSIST)
    private List<OrderedDish> orderedDishes;

    protected Order() {

    }

    public Order(User u, Address address) {
        this.user = u;
        this.orderedDishes = new ArrayList<>();
        this.address = address;
        this.status = OrderStatus.Received;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<Dish> getOrderedDishes() {
        return this.orderedDishes.stream().map(OrderedDish::getDish).toList();
    }

    public List<Long> getOrderedDishIds() {
        List<Long> ordered = new ArrayList<>();

        for (OrderedDish od : this.orderedDishes) {
            ordered.add(od.getDishId());
        }

        return Collections.unmodifiableList(ordered);
    }

    public void addDish(Dish dish) {
        this.orderedDishes.add(new OrderedDish(this, dish));
    }

    public Address getAddress() {
        return address;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void process(LocalDateTime orderMoment) {
        this.orderDate = orderMoment;
        for (Dish d : this.getOrderedDishes()) {
            d.prepare();
        }
    }
}
