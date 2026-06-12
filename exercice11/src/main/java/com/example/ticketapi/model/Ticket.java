package com.example.ticketapi.model;

public record Ticket(Long id, String title, TicketPriority priority, TicketStatus status) {

    public Ticket withStatus(TicketStatus newStatus) {
        return new Ticket(id, title, priority, newStatus);
    }
}
