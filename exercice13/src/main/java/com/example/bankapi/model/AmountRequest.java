package com.example.bankapi.model;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AmountRequest(
        @NotNull(message = "Le montant est obligatoire") BigDecimal amount
) {}
