package com.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShopSteps {

    // Services
    private UserRepository userRepository;
    private UserService userService;
    private ProductRepository productRepository;
    private ProductService productService;
    private OrderRepository orderRepository;
    private OrderService orderService;

    // Résultats
    private ConfirmationAccount confirmationAccount;
    private ConfirmationOrder confirmationOrder;
    private List<Product> productResults;
    private Order currentOrder;
    private Exception exception;

    // ─── Création de compte ───────────────────────────────────────────────

    @Given("je suis sur le formulaire d'inscription")
    public void jeSuisSurLeFormulaireInscription() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
    }

    @Given("un utilisateur {string} est déjà inscrit")
    public void unUtilisateurEstDejaInscrit(String username) {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        when(userRepository.existsByUsername(username)).thenReturn(true);
    }

    @When("je m'inscris avec l'email {string}, le nom {string} et le mot de passe {string}")
    public void jeMInscris(String email, String username, String password) {
        exception = null;
        confirmationAccount = null;
        try {
            confirmationAccount = userService.register(username, email, password);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("mon compte est créé avec succès")
    public void monCompteEstCree() {
        assertNull(exception, "Aucune exception attendue");
        assertNotNull(confirmationAccount);
    }

    @Then("je reçois le message de confirmation {string}")
    public void jeRecoisLeMessageDeConfirmation(String message) {
        assertEquals(message, confirmationAccount.getMessage());
    }

    @Then("l'inscription échoue avec le message {string}")
    public void lInscriptionEchoue(String message) {
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    // ─── Connexion ────────────────────────────────────────────────────────

    @Given("un utilisateur {string} avec le mot de passe {string} existe")
    public void unUtilisateurAvecMotDePasseExiste(String username, String password) {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        User user = new User(username, "user@example.com", password);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
    }

    @When("je me connecte avec le nom {string} et le mot de passe {string}")
    public void jeMeConnecte(String username, String password) {
        exception = null;
        try {
            userService.login(username, password);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("je suis redirigé vers la page d'accueil")
    public void jeSuisRedirige() {
        assertNull(exception, "Connexion réussie, aucune exception attendue");
    }

    @Then("je vois le message d'erreur de connexion {string}")
    public void jeVoisLeMessageErreurConnexion(String message) {
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    // ─── Recherche de produits ────────────────────────────────────────────

    @Given("des produits contenant {string} sont disponibles dans le catalogue")
    public void desProduitsContenant(String keyword) {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
        List<Product> products = List.of(
                new Product(1L, "chaussure de sport", 49.99, "sport"),
                new Product(2L, "chaussure de ville", 89.99, "mode")
        );
        when(productRepository.findByNameContaining(keyword)).thenReturn(products);
    }

    @Given("des produits à différents prix sont disponibles dans le catalogue")
    public void desProduitsADifferentsPrix() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
        List<Product> cheapProducts = List.of(
                new Product(1L, "t-shirt", 19.99, "mode"),
                new Product(2L, "chaussette", 5.99, "mode")
        );
        when(productRepository.findByMaxPrice(50.0)).thenReturn(cheapProducts);
    }

    @When("je recherche le mot-clé {string}")
    public void jeRecherche(String keyword) {
        productResults = productService.search(keyword);
    }

    @When("je recherche des produits avec un prix maximum de {double}")
    public void jeRechercheParPrixMax(double maxPrice) {
        productResults = productService.searchByMaxPrice(maxPrice);
    }

    @Then("les résultats contiennent des produits avec {string} dans le nom")
    public void lesResultatsContiennent(String keyword) {
        assertFalse(productResults.isEmpty());
        productResults.forEach(p ->
                assertTrue(p.getName().contains(keyword),
                        "Le produit '" + p.getName() + "' ne contient pas '" + keyword + "'")
        );
    }

    @Then("tous les produits des résultats ont un prix inférieur ou égal à {double}")
    public void tousLesProduitsPrixInferieur(double maxPrice) {
        assertFalse(productResults.isEmpty());
        productResults.forEach(p ->
                assertTrue(p.getPrice() <= maxPrice,
                        "Le produit '" + p.getName() + "' coûte " + p.getPrice())
        );
    }

    // ─── Navigation par catégorie ─────────────────────────────────────────

    @Given("des produits de la catégorie {string} sont disponibles")
    public void desProduitsDeCategorie(String category) {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
        List<Product> sportProducts = List.of(
                new Product(1L, "ballon de foot", 29.99, "sport"),
                new Product(2L, "raquette de tennis", 59.99, "sport")
        );
        when(productRepository.findByCategory(category)).thenReturn(sportProducts);
    }

    @When("je sélectionne la catégorie {string}")
    public void jeSelectionneCategorie(String category) {
        productResults = productService.getByCategory(category);
    }

    @Then("je vois uniquement des produits de la catégorie {string}")
    public void jeVoisUniquementCategorie(String category) {
        assertFalse(productResults.isEmpty());
        productResults.forEach(p ->
                assertEquals(category, p.getCategory())
        );
    }

    // ─── Gestion des commandes – setup commun ────────────────────────────

    private void initOrderService() {
        if (orderRepository == null) orderRepository = mock(OrderRepository.class);
        if (productRepository == null) productRepository = mock(ProductRepository.class);
        orderService = new OrderService(orderRepository, productRepository);
    }

    @Given("une commande avec l'identifiant {long} existe")
    public void uneCommandeExiste(long orderId) {
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        currentOrder = new Order(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(currentOrder));
        initOrderService();
    }

    @Given("aucune commande avec l'identifiant {long}")
    public void aucuneCommande(long orderId) {
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        initOrderService();
    }

    @Given("un produit {string} avec l'identifiant {long} est disponible")
    public void unProduitDisponible(String name, long productId) {
        Product product = new Product(productId, name, 59.99, "mode");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    }

    @Given("une commande avec l'identifiant {long} contenant déjà le produit {long} en quantité {int}")
    public void uneCommandeAvecProduit(long orderId, long productId, int quantity) {
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        Product product = new Product(productId, "produit-" + productId, 29.99, "test");
        currentOrder = new Order(orderId);
        currentOrder.getItems().add(new OrderItem(product, quantity));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(currentOrder));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        initOrderService();
    }

    // ─── Ajout à la commande ──────────────────────────────────────────────

    @When("j'ajoute le produit {long} à la commande {long}")
    public void jAjouteProduit(long productId, long orderId) {
        exception = null;
        try {
            orderService.addProduct(orderId, productId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("le produit {string} est dans la commande avec une quantité de {int}")
    public void leProduitEstDansLaCommandeAvecQuantite(String name, int quantity) {
        assertNull(exception);
        OrderItem item = currentOrder.getItems().stream()
                .filter(i -> i.getProduct().getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Produit '" + name + "' absent de la commande"));
        assertEquals(quantity, item.getQuantity());
    }

    @Then("le produit avec l'identifiant {long} est dans la commande avec une quantité de {int}")
    public void leProduitIdEstDansLaCommandeAvecQuantite(long productId, int quantity) {
        assertNull(exception);
        OrderItem item = currentOrder.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Produit id=" + productId + " absent de la commande"));
        assertEquals(quantity, item.getQuantity());
    }

    @Then("une erreur de commande {string} est renvoyée")
    public void uneErreurEstRenvoyee(String message) {
        assertNotNull(exception, "Une exception était attendue");
        assertEquals(message, exception.getMessage());
    }

    // ─── Suppression de la commande ───────────────────────────────────────

    @When("je supprime le produit {long} de la commande {long}")
    public void jeSupprimeProduit(long productId, long orderId) {
        exception = null;
        try {
            orderService.removeProduct(orderId, productId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("le produit avec l'identifiant {long} n'est plus dans la commande {long}")
    public void leProduitNestPlusDansLaCommande(long productId, long orderId) {
        assertNull(exception);
        boolean present = currentOrder.getItems().stream()
                .anyMatch(i -> i.getProduct().getId().equals(productId));
        assertFalse(present, "Le produit id=" + productId + " devrait être retiré de la commande");
    }

    // ─── Validation de commande ───────────────────────────────────────────

    @When("je valide la commande {long}")
    public void jeValideCommande(long orderId) {
        exception = null;
        confirmationOrder = null;
        try {
            confirmationOrder = orderService.validate(orderId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("la commande est confirmée")
    public void laCommandeEstConfirmee() {
        assertNull(exception, "Aucune exception attendue");
        assertNotNull(confirmationOrder);
        assertTrue(currentOrder.isValidated());
    }

    @Then("je reçois un message de confirmation de commande {string}")
    public void jeRecoisMessageConfirmationCommande(String message) {
        assertEquals(message, confirmationOrder.getMessage());
    }
}
