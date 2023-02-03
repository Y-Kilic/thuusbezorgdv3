package com.example.dishservice;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime now();
}
