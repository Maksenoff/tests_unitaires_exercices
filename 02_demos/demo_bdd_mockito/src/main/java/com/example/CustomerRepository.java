package com.example;

public interface CustomerRepository {
    boolean existsByEmail(String email);

    int countOrdersByEmail(String email);
}
