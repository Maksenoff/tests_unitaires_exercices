package com.example;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long id);
    void save(Order order);
}
