package com.example.roomapi.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(Long id) {
        super("Salle introuvable avec l'identifiant " + id);
    }
}
