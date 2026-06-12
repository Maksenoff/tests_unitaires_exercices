package com.example;

import java.math.BigDecimal;

public record OrderReceipt(
        String productReference,
        int quantity,
        BigDecimal totalAmount,
        String message
) {
}
