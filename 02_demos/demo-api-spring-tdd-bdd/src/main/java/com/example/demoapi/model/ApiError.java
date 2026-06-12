package com.example.demoapi.model;

import java.time.Instant;

/**
 * Format simple d'erreur JSON.
 *
 * Exemple :
 * {
 *   "status": 404,
 *   "message": "Élément introuvable",
 *   "timestamp": "..."
 * }
 */
public record ApiError(int status, String message, Instant timestamp) {

    public static ApiError of(int status, String message) {
        return new ApiError(status, message, Instant.now());
    }
}
