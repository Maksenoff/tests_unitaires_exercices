package com.example;

public class PriceCalculator {
    public double calculateTotalPrice(double unitPrice, int quantity) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price must not be negative");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must not be negative");
        }

        return unitPrice * quantity;
    }

    public double applyDiscount(double price, double discountRate) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }

        if (discountRate < 0) {
            throw new IllegalArgumentException("Discount rate must not be negative");
        }

        return price - price * discountRate;
    }

    public double calculateVat(double price, double vatRate) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }

        if (vatRate < 0) {
            throw new IllegalArgumentException("VAT rate must not be negative");
        }

        return price * vatRate;
    }

    public double calculatePriceWithVat(double price, double vatRate) {
        return price + calculateVat(price, vatRate);
    }
}
