package com.example.ticketapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(
        @NotBlank(message = "Le titre est obligatoire")
        @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
        String title,

        @NotNull(message = "La priorité est obligatoire")
        TicketPriority priority
) {
}
