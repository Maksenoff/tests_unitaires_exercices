package com.example.roomapi.integration;

import com.example.roomapi.repository.ReservationRepository;
import com.example.roomapi.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    void shouldCreateRoomThenReserveAndCancel() throws Exception {
        MvcResult roomResult = mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Salle intégration\",\"capacity\":5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Salle intégration"))
                .andReturn();

        long roomId = com.fasterxml.jackson.databind.json.JsonMapper.builder().build()
                .readTree(roomResult.getResponse().getContentAsString()).get("id").asLong();

        MvcResult reservationResult = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roomId\":" + roomId + ",\"personName\":\"Alice\",\"startTime\":\"2026-07-01T09:00:00\",\"endTime\":\"2026-07-01T10:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CONFIRMED"))
                .andReturn();

        long reservationId = com.fasterxml.jackson.databind.json.JsonMapper.builder().build()
                .readTree(reservationResult.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(get("/api/reservations/" + reservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personName").value("Alice"));

        mockMvc.perform(patch("/api/reservations/" + reservationId + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }
}
