package com.example;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    Optional<Room> findByCode(String roomCode);

    List<Booking> findBookingsByRoomCode(String roomCode);

    void save(Booking booking);
}