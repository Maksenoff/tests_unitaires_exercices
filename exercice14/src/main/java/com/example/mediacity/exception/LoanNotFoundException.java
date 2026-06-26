package com.example.mediacity.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long id) {
        super("Prêt introuvable : " + id);
    }
}
