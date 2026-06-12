package com.example.roomapi.exception;

public class SlotConflictException extends RuntimeException {

    public SlotConflictException() {
        super("Ce créneau est déjà réservé pour cette salle");
    }
}
