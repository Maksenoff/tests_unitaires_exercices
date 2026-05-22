package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceCalculatorTest {
    @Test
    void shouldCalculateTotalPrice() {
        PriceCalculator priceCalculator = new PriceCalculator();

        double result = priceCalculator.calculateTotalPrice(10.0, 3);

        assertEquals(30.0, result);
    }

    @Test
    void shouldApplyDiscount() {
        PriceCalculator priceCalculator = new PriceCalculator();

        double result = priceCalculator.applyDiscount(100.0, 0.20);

        assertEquals(80.0, result);
    }

    @Test
    void shouldCalculateVat() {
        PriceCalculator priceCalculator = new PriceCalculator();

        double result = priceCalculator.calculateVat(100.0, 0.20);

        assertEquals(20.0, result);
    }

    @Test
    void shouldCalculatePriceWithVat() {
        PriceCalculator priceCalculator = new PriceCalculator();

        double result = priceCalculator.calculatePriceWithVat(100.0, 0.20);

        assertEquals(120.0, result);
    }

    @Test
    void shouldThrowExceptionWhenUnitPriceIsNegative() {
        PriceCalculator priceCalculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> priceCalculator.calculateTotalPrice(-10.0, 3)
        );

        assertEquals("Unit price must not be negative", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        PriceCalculator priceCalculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> priceCalculator.calculateTotalPrice(10.0, -3)
        );

        assertEquals("Quantity must not be negative", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDiscountRateIsNegative() {
        PriceCalculator priceCalculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> priceCalculator.applyDiscount(100.0, -0.20)
        );

        assertEquals("Discount rate must not be negative", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenVatRateIsNegative() {
        PriceCalculator priceCalculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> priceCalculator.calculateVat(100.0, -0.20)
        );

        assertEquals("VAT rate must not be negative", exception.getMessage());
    }
}
