package com.example.bankapi.bdd;

import com.example.bankapi.repository.AccountRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BankApiStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ResultActions lastResult;
    private String lastAccountNumber;

    @Before
    public void reset() {
        accountRepository.deleteAll();
        lastAccountNumber = null;
    }

    @Given("aucun compte n'existe")
    public void noAccountExists() {
        accountRepository.deleteAll();
    }

    @Given("un compte {string} au nom de {string} existe avec un solde de {int} euros")
    public void accountExistsWithBalance(String number, String holder, int balance) throws Exception {
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\":\"" + number + "\",\"holder\":\"" + holder + "\"}"));
        if (balance > 0) {
            mockMvc.perform(post("/accounts/" + number + "/deposit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"amount\":" + balance + "}"));
        }
        lastAccountNumber = number;
    }

    @Given("un second compte {string} au nom de {string} existe")
    public void secondAccountExists(String number, String holder) throws Exception {
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\":\"" + number + "\",\"holder\":\"" + holder + "\"}"));
    }

    @When("je crée un compte avec le numéro {string} au nom de {string}")
    public void createAccount(String number, String holder) throws Exception {
        lastResult = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\":\"" + number + "\",\"holder\":\"" + holder + "\"}"));
        lastAccountNumber = number;
    }

    @When("je dépose {int} euros sur le compte {string}")
    public void deposit(int amount, String number) throws Exception {
        lastResult = mockMvc.perform(post("/accounts/" + number + "/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":" + amount + "}"));
    }

    @When("je retire {int} euros du compte {string}")
    public void withdraw(int amount, String number) throws Exception {
        lastResult = mockMvc.perform(post("/accounts/" + number + "/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":" + amount + "}"));
    }

    @When("j'effectue un virement de {int} euros du compte {string} vers le compte {string}")
    public void transfer(int amount, String from, String to) throws Exception {
        lastResult = mockMvc.perform(post("/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fromNumber\":\"" + from + "\",\"toNumber\":\"" + to + "\",\"amount\":" + amount + "}"));
    }

    @Then("la réponse HTTP doit être {int}")
    public void responseStatusShouldBe(int expectedStatus) throws Exception {
        lastResult.andExpect(status().is(expectedStatus));
    }

    @Then("le solde du compte {string} doit être {int} euros")
    public void balanceShouldBe(String number, int expectedBalance) throws Exception {
        lastResult.andExpect(jsonPath("$.balance").value(expectedBalance));
    }
}
