package com.example.ticketapi.controller;

import com.example.ticketapi.exception.InvalidStatusTransitionException;
import com.example.ticketapi.exception.TicketNotFoundException;
import com.example.ticketapi.model.Ticket;
import com.example.ticketapi.model.TicketPriority;
import com.example.ticketapi.model.TicketStatus;
import com.example.ticketapi.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService service;

    @Test
    void shouldReturnCreated_whenTicketIsValid() throws Exception {
        when(service.create("Problème réseau", TicketPriority.HIGH))
                .thenReturn(new Ticket(1L, "Problème réseau", TicketPriority.HIGH, TicketStatus.OPEN));

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Problème réseau\",\"priority\":\"HIGH\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/tickets/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void shouldReturnBadRequest_whenTitleIsMissing() throws Exception {
        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"priority\":\"HIGH\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturnBadRequest_whenPriorityIsMissing() throws Exception {
        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Ticket valide\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturnOk_whenTicketExists() throws Exception {
        when(service.getById(1L))
                .thenReturn(new Ticket(1L, "Bug", TicketPriority.LOW, TicketStatus.OPEN));

        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Bug"));
    }

    @Test
    void shouldReturnNotFound_whenTicketDoesNotExist() throws Exception {
        when(service.getById(99L)).thenThrow(new TicketNotFoundException(99L));

        mockMvc.perform(get("/api/tickets/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturnConflict_whenStatusTransitionIsInvalid() throws Exception {
        when(service.updateStatus(1L, TicketStatus.OPEN))
                .thenThrow(new InvalidStatusTransitionException(TicketStatus.RESOLVED, TicketStatus.OPEN));

        mockMvc.perform(patch("/api/tickets/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"OPEN\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }
}
