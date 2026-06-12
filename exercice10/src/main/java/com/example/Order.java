package com.example;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private List<OrderItem> items;
    private boolean validated;

    public Order(Long id) {
        this.id = id;
        this.items = new ArrayList<>();
        this.validated = false;
    }

    public Long getId() { return id; }
    public List<OrderItem> getItems() { return items; }
    public boolean isValidated() { return validated; }
    public void setValidated(boolean validated) { this.validated = validated; }
}
