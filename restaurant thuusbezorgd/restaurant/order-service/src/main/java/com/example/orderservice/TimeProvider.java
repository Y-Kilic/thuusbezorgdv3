package com.example.orderservice;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime now();
}
