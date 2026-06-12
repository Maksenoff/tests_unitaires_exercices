package com.example.roomapi.controller;

import com.example.roomapi.exception.ReservationNotFoundException;
import com.example.roomapi.exception.RoomNotFoundException;
import com.example.roomapi.exception.SlotConflictException;
import com.example.roomapi.model.Reservation;
import com.example.roomapi.model.ReservationStatus;
import com.example.roomapi.model.Room;
import com.example.roomapi.service.ReservationService;
import com.example.roomapi.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({RoomController.class, ReservationController.class})
class RoomControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    @MockitoBean
    private ReservationService reservationService;

    @Test
    void shouldReturnCreated_whenRoomIsValid() throws Exception {
        // Arrange
        when(roomService.create("Salle A", 10)).thenReturn(new Room(1L, "Salle A", 10));

        // Act + Assert
        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Salle A\",\"capacity\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Salle A"));
    }

    @Test
    void shouldReturnBadRequest_whenRoomNameIsBlank() throws Exception {
        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"capacity\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturnCreated_whenReservationIsValid() throws Exception {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2026, 7, 1, 9, 0);
        LocalDateTime end = LocalDateTime.of(2026, 7, 1, 10, 0);
        Reservation reservation = new Reservation(1L, 1L, "Alice", start, end, ReservationStatus.CONFIRMED);
        when(reservationService.create(eq(1L), eq("Alice"), any(), any())).thenReturn(reservation);

        // Act + Assert
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roomId\":1,\"personName\":\"Alice\",\"startTime\":\"2026-07-01T09:00:00\",\"endTime\":\"2026-07-01T10:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void shouldReturnNotFound_whenReservationDoesNotExist() throws Exception {
        // Arrange
        when(reservationService.getById(99L)).thenThrow(new ReservationNotFoundException(99L));

        // Act + Assert
        mockMvc.perform(get("/api/reservations/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturnNotFound_whenRoomDoesNotExistForReservation() throws Exception {
        // Arrange
        when(reservationService.create(eq(99L), any(), any(), any()))
                .thenThrow(new RoomNotFoundException(99L));

        // Act + Assert
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roomId\":99,\"personName\":\"Alice\",\"startTime\":\"2026-07-01T09:00:00\",\"endTime\":\"2026-07-01T10:00:00\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturnConflict_whenSlotIsAlreadyBooked() throws Exception {
        // Arrange
        when(reservationService.create(eq(1L), any(), any(), any()))
                .thenThrow(new SlotConflictException());

        // Act + Assert
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roomId\":1,\"personName\":\"Alice\",\"startTime\":\"2026-07-01T09:00:00\",\"endTime\":\"2026-07-01T10:00:00\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }
}
