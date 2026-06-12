package com.example.roomapi.service;

import com.example.roomapi.exception.AlreadyCancelledException;
import com.example.roomapi.exception.ReservationNotFoundException;
import com.example.roomapi.exception.RoomNotFoundException;
import com.example.roomapi.exception.SlotConflictException;
import com.example.roomapi.model.Reservation;
import com.example.roomapi.model.ReservationStatus;
import com.example.roomapi.repository.ReservationRepository;
import com.example.roomapi.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public Reservation create(Long roomId, String personName, LocalDateTime startTime, LocalDateTime endTime) {
        roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        if (personName == null || personName.isBlank()) {
            throw new IllegalArgumentException("Le nom du réservant est obligatoire");
        }
        if (endTime == null || startTime == null || !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début");
        }

        boolean hasConflict = reservationRepository.findByRoomId(roomId).stream()
                .filter(r -> r.status() == ReservationStatus.CONFIRMED)
                .anyMatch(r -> startTime.isBefore(r.endTime()) && endTime.isAfter(r.startTime()));

        if (hasConflict) {
            throw new SlotConflictException();
        }

        return reservationRepository.save(roomId, personName.trim(), startTime, endTime, ReservationStatus.CONFIRMED);
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public Reservation cancel(Long id) {
        Reservation reservation = getById(id);
        if (reservation.status() == ReservationStatus.CANCELLED) {
            throw new AlreadyCancelledException();
        }
        Reservation cancelled = reservation.withStatus(ReservationStatus.CANCELLED);
        return reservationRepository.update(cancelled);
    }

    public void deleteAll() {
        reservationRepository.deleteAll();
    }
}
