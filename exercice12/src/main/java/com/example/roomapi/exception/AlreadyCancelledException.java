package com.example.roomapi.exception;

public class AlreadyCancelledException extends RuntimeException {

    public AlreadyCancelledException() {
        super("Cette réservation est déjà annulée");
    }
}
