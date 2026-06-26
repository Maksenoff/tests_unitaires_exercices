package com.example.mediacity.exception;

public class MemberSuspendedException extends RuntimeException {
    public MemberSuspendedException(String name) {
        super("L'adhérent " + name + " est suspendu et ne peut pas effectuer d'emprunt ou de réservation");
    }
}
