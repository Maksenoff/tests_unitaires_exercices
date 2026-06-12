package com.example;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findByNameContaining(String keyword);
    List<Product> findByMaxPrice(double maxPrice);
    List<Product> findByCategory(String category);
    Optional<Product> findById(Long id);
}
