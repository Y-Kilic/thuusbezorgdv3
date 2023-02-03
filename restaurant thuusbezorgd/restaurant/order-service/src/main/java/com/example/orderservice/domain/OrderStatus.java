package com.example.orderservice.domain;

public enum OrderStatus {
    Received,
    InPreparation,
    ReadyForDelivery,
    Underway,
    Delivered,
    Disputed;

    public OrderStatus next() {
        return switch (this) {
            case Received -> InPreparation;
            case InPreparation -> ReadyForDelivery;
            case ReadyForDelivery -> Underway;
            case Underway -> Delivered;
            case Delivered -> Delivered;
            case Disputed -> Disputed;
        };
    }
}
