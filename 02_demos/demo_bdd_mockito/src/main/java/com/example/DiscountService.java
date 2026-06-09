package com.example;

public class DiscountService {
    private final CustomerRepository customerRepository;

    public DiscountService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public int calculateDiscountRate(String email) {
        if (!customerRepository.existsByEmail(email)) {
            return 0;
        }

        int orderCount = customerRepository.countOrdersByEmail(email);

        if (orderCount >= 10) {
            return 20;
        }

        if (orderCount >= 5) {
            return 10;
        }

        return 0;
    }
}
