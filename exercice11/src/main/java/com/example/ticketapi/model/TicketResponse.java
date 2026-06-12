package com.example.ticketapi.model;

public record TicketResponse(Long id, String title, TicketPriority priority, TicketStatus status) {

    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(ticket.id(), ticket.title(), ticket.priority(), ticket.status());
    }
}
