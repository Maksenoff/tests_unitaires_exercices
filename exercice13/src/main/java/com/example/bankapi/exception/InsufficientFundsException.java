package com.example.bankapi.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Solde insuffisant pour effectuer cette opération");
    }
}
