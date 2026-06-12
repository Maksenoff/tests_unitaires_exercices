package com.example.ticketapi.service;

import com.example.ticketapi.exception.InvalidStatusTransitionException;
import com.example.ticketapi.exception.TicketNotFoundException;
import com.example.ticketapi.model.Ticket;
import com.example.ticketapi.model.TicketPriority;
import com.example.ticketapi.model.TicketStatus;
import com.example.ticketapi.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository repository;

    @InjectMocks
    private TicketService service;

    @Test
    void shouldCreateTicket_whenDataIsValid() {
        Ticket expected = new Ticket(1L, "Problème réseau", TicketPriority.HIGH, TicketStatus.OPEN);
        when(repository.save("Problème réseau", TicketPriority.HIGH)).thenReturn(expected);

        Ticket result = service.create("Problème réseau", TicketPriority.HIGH);

        assertEquals(1L, result.id());
        assertEquals("Problème réseau", result.title());
        assertEquals(TicketStatus.OPEN, result.status());
        verify(repository).save("Problème réseau", TicketPriority.HIGH);
    }

    @Test
    void shouldCreateTicketWithOpenStatus_byDefault() {
        Ticket expected = new Ticket(1L, "Bug critique", TicketPriority.MEDIUM, TicketStatus.OPEN);
        when(repository.save("Bug critique", TicketPriority.MEDIUM)).thenReturn(expected);

        Ticket result = service.create("Bug critique", TicketPriority.MEDIUM);

        assertEquals(TicketStatus.OPEN, result.status());
    }

    @Test
    void shouldThrowException_whenTitleIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> service.create("  ", TicketPriority.LOW));
    }

    @Test
    void shouldThrowException_whenTitleIsTooShort() {
        assertThrows(IllegalArgumentException.class, () -> service.create("AB", TicketPriority.LOW));
    }

    @Test
    void shouldReturnTicket_whenIdExists() {
        Ticket ticket = new Ticket(1L, "Ticket test", TicketPriority.LOW, TicketStatus.OPEN);
        when(repository.findById(1L)).thenReturn(Optional.of(ticket));

        Ticket result = service.getById(1L);

        assertEquals(1L, result.id());
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowNotFoundException_whenIdDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void shouldUpdateStatus_whenTransitionIsAllowed() {
        Ticket ticket = new Ticket(1L, "Ticket test", TicketPriority.LOW, TicketStatus.OPEN);
        Ticket updated = ticket.withStatus(TicketStatus.IN_PROGRESS);
        when(repository.findById(1L)).thenReturn(Optional.of(ticket));
        when(repository.update(updated)).thenReturn(updated);

        Ticket result = service.updateStatus(1L, TicketStatus.IN_PROGRESS);

        assertEquals(TicketStatus.IN_PROGRESS, result.status());
    }

    @Test
    void shouldThrowConflict_whenTicketIsAlreadyResolved() {
        Ticket ticket = new Ticket(1L, "Ticket test", TicketPriority.LOW, TicketStatus.RESOLVED);
        when(repository.findById(1L)).thenReturn(Optional.of(ticket));

        assertThrows(InvalidStatusTransitionException.class, () -> service.updateStatus(1L, TicketStatus.IN_PROGRESS));
    }

    @Test
    void shouldThrowConflict_whenTransitionIsNotAllowed() {
        Ticket ticket = new Ticket(1L, "Ticket test", TicketPriority.LOW, TicketStatus.IN_PROGRESS);
        when(repository.findById(1L)).thenReturn(Optional.of(ticket));

        assertThrows(InvalidStatusTransitionException.class, () -> service.updateStatus(1L, TicketStatus.OPEN));
    }
}
