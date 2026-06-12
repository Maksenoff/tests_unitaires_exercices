package com.example.roomapi.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(Long id) {
        super("Réservation introuvable avec l'identifiant " + id);
    }
}
