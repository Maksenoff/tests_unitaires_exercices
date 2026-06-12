package com.example.ticketapi.exception;

import com.example.ticketapi.model.TicketStatus;

public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(TicketStatus from, TicketStatus to) {
        super("Transition interdite : " + from + " -> " + to);
    }
}
