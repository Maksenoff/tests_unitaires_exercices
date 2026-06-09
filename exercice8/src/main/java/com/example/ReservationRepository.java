package com.example;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findBySalle(String codeSalle);
}
