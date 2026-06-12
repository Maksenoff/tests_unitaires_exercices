package com.example.ticketapi.repository;

import com.example.ticketapi.model.Ticket;
import com.example.ticketapi.model.TicketPriority;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    Ticket save(String title, TicketPriority priority);

    Ticket update(Ticket ticket);

    Optional<Ticket> findById(Long id);

    List<Ticket> findAll();

    void deleteAll();
}
