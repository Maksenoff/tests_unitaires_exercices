package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderService {
    private final ProductCatalog productCatalog;

    public OrderService(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public OrderReceipt placeOrder(String customerEmail, CustomerStatus status, String productReference, int quantity) {
        Product product = productCatalog.findByReference(productReference)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (quantity > product.stock()) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        BigDecimal total = product.unitPrice()
                .multiply(BigDecimal.valueOf(quantity))
                .multiply(BigDecimal.ONE.subtract(discountRate(status)))
                .setScale(2, RoundingMode.HALF_UP);

        return new OrderReceipt(
                product.reference(),
                quantity,
                total,
                "Order accepted for " + customerEmail
        );
    }

    private BigDecimal discountRate(CustomerStatus status) {
        return switch (status) {
            case STANDARD -> BigDecimal.ZERO;
            case PREMIUM -> new BigDecimal("0.10");
            case VIP -> new BigDecimal("0.20");
        };
    }
}
