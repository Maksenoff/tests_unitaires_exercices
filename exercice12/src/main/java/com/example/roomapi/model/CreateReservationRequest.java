package com.example.roomapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateReservationRequest(
        @NotNull(message = "L'identifiant de la salle est obligatoire")
        Long roomId,

        @NotBlank(message = "Le nom du réservant est obligatoire")
        String personName,

        @NotNull(message = "La date de début est obligatoire")
        LocalDateTime startTime,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDateTime endTime
) {
}
