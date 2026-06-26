package com.example.mediacity.service;

import com.example.mediacity.exception.MemberSuspendedException;
import com.example.mediacity.model.Book;
import com.example.mediacity.model.Member;
import com.example.mediacity.model.Reservation;
import com.example.mediacity.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation reserve(Member member, Book book, LocalDate date) {
        if (member.suspended()) {
            throw new MemberSuspendedException(member.name());
        }
        return reservationRepository.save(member.id(), book.id(), date);
    }

    public List<Reservation> getReservationsForBook(Long bookId) {
        return reservationRepository.findByBookId(bookId);
    }

    public Optional<Reservation> getNextReservation(Long bookId) {
        return reservationRepository.findFirstByBookId(bookId);
    }
}
