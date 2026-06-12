package com.example.ticketapi.exception;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(Long id) {
        super("Ticket introuvable avec l'identifiant " + id);
    }
}
