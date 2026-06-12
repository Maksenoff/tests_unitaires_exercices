package com.example.ticketapi.bdd;

import com.example.ticketapi.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TicketApiStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private ResultActions lastResult;
    private Long createdTicketId;

    @Before
    public void reset() {
        repository.deleteAll();
        createdTicketId = null;
    }

    @Given("aucun ticket n'existe")
    public void noTicketExists() {
        repository.deleteAll();
    }

    @Given("un ticket existe avec le titre {string} et la priorité {string}")
    public void ticketExistsWithTitle(String title, String priority) throws Exception {
        lastResult = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"" + title + "\",\"priority\":\"" + priority + "\"}"));
        String body = lastResult.andReturn().getResponse().getContentAsString();
        createdTicketId = objectMapper.readTree(body).get("id").asLong();
    }

    @Given("un ticket résolu existe")
    public void resolvedTicketExists() throws Exception {
        ticketExistsWithTitle("Ticket résolu", "LOW");
        mockMvc.perform(patch("/api/tickets/" + createdTicketId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"RESOLVED\"}"));
    }

    @When("je crée un ticket avec le titre {string} et la priorité {string}")
    public void createTicket(String title, String priority) throws Exception {
        lastResult = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"" + title + "\",\"priority\":\"" + priority + "\"}"));
        String body = lastResult.andReturn().getResponse().getContentAsString();
        if (lastResult.andReturn().getResponse().getStatus() == 201) {
            createdTicketId = objectMapper.readTree(body).get("id").asLong();
        }
    }

    @When("je modifie le statut du ticket vers {string}")
    public void updateTicketStatus(String status) throws Exception {
        lastResult = mockMvc.perform(patch("/api/tickets/" + createdTicketId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"" + status + "\"}"));
    }

    @When("je tente de modifier son statut vers {string}")
    public void tryUpdateTicketStatus(String status) throws Exception {
        lastResult = mockMvc.perform(patch("/api/tickets/" + createdTicketId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"" + status + "\"}"));
    }

    @When("je consulte le ticket avec l'identifiant {long}")
    public void getTicketById(Long id) throws Exception {
        lastResult = mockMvc.perform(get("/api/tickets/" + id));
    }

    @Then("la réponse HTTP doit être {int}")
    public void responseStatusShouldBe(int expectedStatus) throws Exception {
        lastResult.andExpect(status().is(expectedStatus));
    }

    @Then("le ticket est créé avec le statut {string}")
    public void ticketHasStatus(String expectedStatus) throws Exception {
        lastResult.andExpect(jsonPath("$.status").value(expectedStatus));
    }

    @Then("le ticket a le statut {string}")
    public void ticketStatusIs(String expectedStatus) throws Exception {
        lastResult.andExpect(jsonPath("$.status").value(expectedStatus));
    }
}
