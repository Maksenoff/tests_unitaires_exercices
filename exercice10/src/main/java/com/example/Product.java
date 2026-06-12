package com.example;

public class Product {
    private Long id;
    private String name;
    private double price;
    private String category;

    public Product(Long id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
}
