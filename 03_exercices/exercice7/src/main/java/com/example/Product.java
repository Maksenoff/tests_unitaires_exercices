package com.example;

import java.math.BigDecimal;

public record Product(
        String reference,
        String name,
        BigDecimal unitPrice,
        int stock
) {
}
