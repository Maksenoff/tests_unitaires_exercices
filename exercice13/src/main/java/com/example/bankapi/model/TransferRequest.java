package com.example.bankapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransferRequest(
        @NotBlank(message = "Le compte émetteur est obligatoire") String fromNumber,
        @NotBlank(message = "Le compte destinataire est obligatoire") String toNumber,
        @NotNull(message = "Le montant est obligatoire") BigDecimal amount
) {}
