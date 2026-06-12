package com.example.roomapi.service;

import com.example.roomapi.exception.AlreadyCancelledException;
import com.example.roomapi.exception.ReservationNotFoundException;
import com.example.roomapi.exception.RoomNotFoundException;
import com.example.roomapi.exception.SlotConflictException;
import com.example.roomapi.model.Reservation;
import com.example.roomapi.model.ReservationStatus;
import com.example.roomapi.model.Room;
import com.example.roomapi.repository.ReservationRepository;
import com.example.roomapi.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private ReservationService service;

    private final LocalDateTime start = LocalDateTime.of(2026, 7, 1, 9, 0);
    private final LocalDateTime end = LocalDateTime.of(2026, 7, 1, 10, 0);

    @Test
    void shouldCreateReservation_whenRoomExistsAndSlotIsFree() {
        Room room = new Room(1L, "Salle A", 10);
        Reservation expected = new Reservation(1L, 1L, "Alice", start, end, ReservationStatus.CONFIRMED);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(reservationRepository.findByRoomId(1L)).thenReturn(List.of());
        when(reservationRepository.save(eq(1L), eq("Alice"), eq(start), eq(end), eq(ReservationStatus.CONFIRMED))).thenReturn(expected);

        Reservation result = service.create(1L, "Alice", start, end);

        assertEquals(1L, result.id());
        assertEquals(ReservationStatus.CONFIRMED, result.status());
    }

    @Test
    void shouldThrowNotFoundException_whenRoomDoesNotExist() {
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> service.create(99L, "Alice", start, end));
    }

    @Test
    void shouldThrowException_whenEndTimeIsBeforeStartTime() {
        Room room = new Room(1L, "Salle A", 10);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        assertThrows(IllegalArgumentException.class, () -> service.create(1L, "Alice", end, start));
    }

    @Test
    void shouldThrowConflict_whenSlotOverlapsExistingReservation() {
        Room room = new Room(1L, "Salle A", 10);
        Reservation existing = new Reservation(1L, 1L, "Bob", start, end, ReservationStatus.CONFIRMED);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(reservationRepository.findByRoomId(1L)).thenReturn(List.of(existing));

        LocalDateTime newStart = start.plusMinutes(30);
        LocalDateTime newEnd = end.plusMinutes(30);

        assertThrows(SlotConflictException.class, () -> service.create(1L, "Alice", newStart, newEnd));
    }

    @Test
    void shouldCancelReservation_whenStatusIsConfirmed() {
        Reservation reservation = new Reservation(1L, 1L, "Alice", start, end, ReservationStatus.CONFIRMED);
        Reservation cancelled = reservation.withStatus(ReservationStatus.CANCELLED);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.update(cancelled)).thenReturn(cancelled);

        Reservation result = service.cancel(1L);

        assertEquals(ReservationStatus.CANCELLED, result.status());
    }

    @Test
    void shouldThrowConflict_whenCancellingAlreadyCancelledReservation() {
        Reservation reservation = new Reservation(1L, 1L, "Alice", start, end, ReservationStatus.CANCELLED);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThrows(AlreadyCancelledException.class, () -> service.cancel(1L));
    }

    @Test
    void shouldThrowNotFoundException_whenReservationDoesNotExist() {
        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> service.getById(99L));
    }
}
