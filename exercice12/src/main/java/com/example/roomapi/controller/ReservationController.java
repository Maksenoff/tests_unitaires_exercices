package com.example.roomapi.controller;

import com.example.roomapi.model.CreateReservationRequest;
import com.example.roomapi.model.ReservationResponse;
import com.example.roomapi.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody CreateReservationRequest request) {
        var reservation = service.create(request.roomId(), request.personName(), request.startTime(), request.endTime());
        var response = ReservationResponse.from(reservation);
        return ResponseEntity
                .created(URI.create("/api/reservations/" + response.id()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getById(@PathVariable Long id) {
        var reservation = service.getById(id);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancel(@PathVariable Long id) {
        var reservation = service.cancel(id);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }
}
