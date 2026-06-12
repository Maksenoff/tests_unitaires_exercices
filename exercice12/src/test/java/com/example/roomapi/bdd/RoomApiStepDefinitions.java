package com.example.roomapi.bdd;

import com.example.roomapi.repository.ReservationRepository;
import com.example.roomapi.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomApiStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ResultActions lastResult;
    private Long createdRoomId;

    @Before
    public void reset() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        createdRoomId = null;
    }

    @Given("aucune salle n'existe")
    public void noRoomExists() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Given("une salle {string} de capacité {int} existe")
    public void roomExists(String name, int capacity) throws Exception {
        var result = mockMvc.perform(post("/api/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\",\"capacity\":" + capacity + "}"));
        String body = result.andReturn().getResponse().getContentAsString();
        createdRoomId = objectMapper.readTree(body).get("id").asLong();
    }

    @Given("une réservation existe pour cette salle de {string} à {string}")
    public void reservationExistsForRoom(String start, String end) throws Exception {
        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"roomId\":" + createdRoomId + ",\"personName\":\"Bob\",\"startTime\":\"" + start + ":00\",\"endTime\":\"" + end + ":00\"}"));
    }

    @When("je crée une réservation pour cette salle au nom de {string} du {string} au {string}")
    public void createReservation(String person, String start, String end) throws Exception {
        lastResult = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"roomId\":" + createdRoomId + ",\"personName\":\"" + person + "\",\"startTime\":\"" + start + ":00\",\"endTime\":\"" + end + ":00\"}"));
    }

    @When("je tente de créer une réservation pour la salle avec l'identifiant {long}")
    public void tryCreateReservationForUnknownRoom(Long roomId) throws Exception {
        lastResult = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"roomId\":" + roomId + ",\"personName\":\"Alice\",\"startTime\":\"2026-07-01T09:00:00\",\"endTime\":\"2026-07-01T10:00:00\"}"));
    }

    @When("je tente de créer une réservation pour cette salle de {string} à {string}")
    public void tryCreateOverlappingReservation(String start, String end) throws Exception {
        lastResult = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"roomId\":" + createdRoomId + ",\"personName\":\"Alice\",\"startTime\":\"" + start + ":00\",\"endTime\":\"" + end + ":00\"}"));
    }

    @Then("la réponse HTTP doit être {int}")
    public void responseStatusShouldBe(int expectedStatus) throws Exception {
        lastResult.andExpect(status().is(expectedStatus));
    }
}
