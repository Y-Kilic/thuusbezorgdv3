package com.example.deliveryservice;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime now();
}
