package com.example.reviewsservice.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "Delivery.findRandom", resultClass = Delivery.class,
        query = "select * from delivery order by random() limit 1")
public class Delivery {
    @Id
    @GeneratedValue
    private Long id;

    private boolean completed;

    public Long getId() {
        return id;
    }

    public Rider getRider() {
        return rider;
    }

    public Order getOrder() {
        return order;
    }

    @ManyToOne
    private Rider rider;

    @ManyToOne
    private Order order;

    protected Delivery(){}

    public Delivery(Order order, Rider rider){
        this.order = order;
        this.rider = rider;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted(){
        this.completed = true;
        this.order.setStatus(nl.hu.inno.thuusbezorgd.domain.OrderStatus.Delivered);
    }
}
