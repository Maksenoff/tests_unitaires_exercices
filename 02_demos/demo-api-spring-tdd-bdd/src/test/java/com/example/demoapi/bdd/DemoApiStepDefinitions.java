package com.example.demoapi.bdd;

import com.example.demoapi.repository.DemoItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Step definitions Cucumber.
 *
 * Chaque méthode Java est reliée à une phrase du fichier .feature.
 * Les expressions {string}, {int}, {long} permettent de récupérer des valeurs du scénario.
 *
 * Cette classe montre aussi deux notions BDD complémentaires :
 *
 * 1. Scenario Outline + Examples :
 *    permet de rejouer un même scénario avec plusieurs jeux de données.
 *
 * 2. DataTable :
 *    permet de passer un tableau de données directement à une step.
 */
public class DemoApiStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoItemRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private ResultActions lastResult;
    private Long createdItemId;

    @Given("aucun element n existe dans l API")
    public void noItemExists() {
        repository.deleteAll();
        createdItemId = null;
    }

    /**
     * Exemple de DataTable Cucumber.
     *
     * Dans le fichier .feature, on peut écrire :
     *
     * And les elements suivants existent dans l API
     *   | label     |
     *   | premier   |
     *   | deuxieme  |
     *
     * Cucumber transforme ce tableau en objet DataTable.
     * Ici, on le convertit en liste de Map.
     *
     * Chaque ligne du tableau devient une Map :
     * - la clé correspond au nom de la colonne ;
     * - la valeur correspond au contenu de la cellule.
     *
     * Exemple :
     * { "label" -> "premier" }
     */
    @Given("les elements suivants existent dans l API")
    public void followingItemsExist(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            String label = row.get("label");
            repository.save(label);
        }
    }

    @When("je cree un element avec le libelle {string}")
    public void createItemWithLabel(String label) throws Exception {
        lastResult = mockMvc.perform(post("/api/demo-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"label\":\"" + label + "\"}"));

        var responseBody = lastResult.andReturn().getResponse().getContentAsString();
        if (!responseBody.isBlank() && lastResult.andReturn().getResponse().getStatus() == 201) {
            createdItemId = objectMapper.readTree(responseBody).get("id").asLong();
        }
    }

    @When("je demande l element cree")
    public void getCreatedItem() throws Exception {
        lastResult = mockMvc.perform(get("/api/demo-items/" + createdItemId));
    }

    @When("je demande l element avec l identifiant {long}")
    public void getItemById(Long id) throws Exception {
        lastResult = mockMvc.perform(get("/api/demo-items/" + id));
    }

    @When("je demande la liste des elements")
    public void getAllItems() throws Exception {
        lastResult = mockMvc.perform(get("/api/demo-items"));
    }

    @Then("la reponse HTTP doit etre {int}")
    public void responseStatusShouldBe(int expectedStatus) throws Exception {
        lastResult.andExpect(status().is(expectedStatus));
    }

    @Then("la reponse contient le libelle {string}")
    public void responseShouldContainLabel(String expectedLabel) throws Exception {
        lastResult.andExpect(jsonPath("$.label").value(expectedLabel));
    }

    @Then("la reponse contient un message d erreur")
    public void responseShouldContainErrorMessage() throws Exception {
        lastResult.andExpect(jsonPath("$.message").exists());
    }

    @Then("la reponse contient {int} elements")
    public void responseShouldContainItemCount(int expectedCount) throws Exception {
        lastResult.andExpect(jsonPath("$.length()").value(expectedCount));
    }

    @Then("la reponse contient au moins le libelle {string}")
    public void responseShouldContainAtLeastLabel(String expectedLabel) throws Exception {
        lastResult.andExpect(jsonPath("$[*].label", hasItem(expectedLabel)));
    }
}