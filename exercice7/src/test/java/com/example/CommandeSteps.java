package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandeSteps {
    private ProduitRepository produitRepository;
    private CommandeService commandeService;
    private RecuCommande recu;
    private Exception exception;

    @Given("un produit {string} nommé {string} à {double} euros avec un stock de {int}")
    public void unProduit(String reference, String nom, double prix, int stock) {
        produitRepository = mock(ProduitRepository.class);
        commandeService = new CommandeService(produitRepository);
        Produit produit = new Produit(reference, nom, prix, stock);
        when(produitRepository.findByReference(reference)).thenReturn(Optional.of(produit));
    }

    @Given("aucun produit avec la référence {string}")
    public void aucunProduit(String reference) {
        produitRepository = mock(ProduitRepository.class);
        commandeService = new CommandeService(produitRepository);
        when(produitRepository.findByReference(reference)).thenReturn(Optional.empty());
    }

    @When("un client {word} commande {int} {string}")
    public void unClientCommande(String typeClientStr, int quantite, String reference) {
        recu = null;
        exception = null;
        TypeClient typeClient = TypeClient.valueOf(typeClientStr);
        Commande commande = new Commande("client@example.com", reference, quantite, typeClient);
        try {
            recu = commandeService.passerCommande(commande);
        } catch (CommandeException e) {
            exception = e;
        }
    }

    @Then("la commande est acceptée")
    public void laCommandeEstAcceptee() {
        assertNull(exception, "Aucune exception attendue");
        assertNotNull(recu);
    }

    @Then("le montant total est {double} euros")
    public void leMontantTotalEst(double montant) {
        assertEquals(montant, recu.getMontantTotal(), 0.001);
    }

    @Then("le message de confirmation est {string}")
    public void leMessageDeConfirmationEst(String message) {
        assertEquals(message, recu.getMessage());
    }

    @Then("la commande est refusée avec le message {string}")
    public void laCommandeEstRefuseeAvecLeMessage(String message) {
        assertNotNull(exception, "Une exception était attendue");
        assertEquals(message, exception.getMessage());
    }
}
