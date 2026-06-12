package com.example;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {

    private final RoomRepository roomRepository;
    private final NotificationGateway notificationGateway;

    public BookingService(RoomRepository roomRepository, NotificationGateway notificationGateway) {
        this.roomRepository = roomRepository;
        this.notificationGateway = notificationGateway;
    }

    public BookingReceipt bookRoom(
            String userEmail,
            String roomCode,
            int attendees,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        if (!endDateTime.isAfter(startDateTime)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        Room room = roomRepository.findByCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (attendees > room.capacity()) {
            throw new IllegalArgumentException("Room capacity exceeded");
        }

        List<Booking> existingBookings = roomRepository.findBookingsByRoomCode(roomCode);

        boolean hasConflict = existingBookings.stream()
                .anyMatch(existingBooking -> overlaps(
                        startDateTime,
                        endDateTime,
                        existingBooking.startDateTime(),
                        existingBooking.endDateTime()
                ));

        if (hasConflict) {
            throw new IllegalArgumentException("Room already booked");
        }

        Booking booking = new Booking(userEmail, roomCode, attendees, startDateTime, endDateTime);

        roomRepository.save(booking);
        notificationGateway.sendConfirmation(userEmail, booking);

        return new BookingReceipt(roomCode, userEmail, "Booking confirmed");
    }

    private boolean overlaps(
            LocalDateTime startA,
            LocalDateTime endA,
            LocalDateTime startB,
            LocalDateTime endB
    ) {
        return startA.isBefore(endB) && endA.isAfter(startB);
    }
}
