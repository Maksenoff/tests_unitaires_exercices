package com.example.mediacity.service;

import com.example.mediacity.exception.MemberSuspendedException;
import com.example.mediacity.model.Book;
import com.example.mediacity.model.Member;
import com.example.mediacity.model.Reservation;
import com.example.mediacity.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService service;

    private final LocalDate today = LocalDate.of(2026, 1, 1);
    private final Member activeMember = new Member(1L, "Alice", 0, false);
    private final Member suspendedMember = new Member(2L, "Bob", 3, true);
    private final Book book = new Book(10L, "Dune", "Herbert");

    @Test
    void shouldCreateReservation_whenMemberIsActive() {
        Reservation expected = new Reservation(1L, 1L, 10L, today);
        when(reservationRepository.save(eq(1L), eq(10L), eq(today))).thenReturn(expected);

        Reservation result = service.reserve(activeMember, book, today);

        assertThat(result.memberId()).isEqualTo(1L);
        assertThat(result.bookId()).isEqualTo(10L);
    }

    @Test
    void shouldThrowMemberSuspended_whenMemberIsSuspended() {
        assertThatThrownBy(() -> service.reserve(suspendedMember, book, today))
                .isInstanceOf(MemberSuspendedException.class);
    }

    @Test
    void shouldReturnReservationsForBook() {
        List<Reservation> reservations = List.of(
                new Reservation(1L, 1L, 10L, today),
                new Reservation(2L, 3L, 10L, today)
        );
        when(reservationRepository.findByBookId(10L)).thenReturn(reservations);

        List<Reservation> result = service.getReservationsForBook(10L);

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnNextReservation_whenExists() {
        Reservation next = new Reservation(1L, 1L, 10L, today);
        when(reservationRepository.findFirstByBookId(10L)).thenReturn(Optional.of(next));

        Optional<Reservation> result = service.getNextReservation(10L);

        assertThat(result).isPresent();
        assertThat(result.get().memberId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnEmpty_whenNoReservationForBook() {
        when(reservationRepository.findFirstByBookId(10L)).thenReturn(Optional.empty());

        Optional<Reservation> result = service.getNextReservation(10L);

        assertThat(result).isEmpty();
    }
}
