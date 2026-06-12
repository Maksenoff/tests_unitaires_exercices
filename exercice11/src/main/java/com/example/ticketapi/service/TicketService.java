package com.example.ticketapi.service;

import com.example.ticketapi.exception.InvalidStatusTransitionException;
import com.example.ticketapi.exception.TicketNotFoundException;
import com.example.ticketapi.model.Ticket;
import com.example.ticketapi.model.TicketPriority;
import com.example.ticketapi.model.TicketStatus;
import com.example.ticketapi.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public Ticket create(String title, TicketPriority priority) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }
        if (title.trim().length() < 3) {
            throw new IllegalArgumentException("Le titre doit contenir au moins 3 caractères");
        }
        if (priority == null) {
            throw new IllegalArgumentException("La priorité est obligatoire");
        }
        return repository.save(title.trim(), priority);
    }

    public Ticket getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public List<Ticket> findAll() {
        return repository.findAll();
    }

    public Ticket updateStatus(Long id, TicketStatus newStatus) {
        Ticket ticket = getById(id);
        validateTransition(ticket.status(), newStatus);
        Ticket updated = ticket.withStatus(newStatus);
        return repository.update(updated);
    }

    private void validateTransition(TicketStatus current, TicketStatus target) {
        boolean allowed = switch (current) {
            case OPEN -> target == TicketStatus.IN_PROGRESS || target == TicketStatus.RESOLVED;
            case IN_PROGRESS -> target == TicketStatus.RESOLVED;
            case RESOLVED -> false;
        };
        if (!allowed) {
            throw new InvalidStatusTransitionException(current, target);
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
