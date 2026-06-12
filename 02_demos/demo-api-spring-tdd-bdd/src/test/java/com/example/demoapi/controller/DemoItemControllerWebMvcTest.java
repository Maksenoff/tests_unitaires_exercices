package com.example.demoapi.controller;

import com.example.demoapi.exception.DemoItemNotFoundException;
import com.example.demoapi.model.DemoItem;
import com.example.demoapi.service.DemoItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Démonstration TDD sur la couche contrôleur.
 *
 * @WebMvcTest démarre uniquement la partie web Spring MVC.
 * On ne charge pas toute l'application : le test est plus rapide qu'un test d'intégration complet.
 *
 * MockMvc permet de simuler des requêtes HTTP sans lancer un serveur réel.
 *
 * @MockitoBean remplace le service Spring par un mock Mockito dans le contexte de test.
 */
@WebMvcTest(DemoItemController.class)
class DemoItemControllerWebMvcTest {

    // MockMvc est fourni par Spring Test.
    // Ce n'est pas une classe que nous avons créée.
    // Il permet de simuler des requêtes HTTP vers nos contrôleurs Spring
    // sans démarrer un vrai serveur web.
    // On peut ainsi tester les endpoints REST : méthode HTTP, URL,
    // corps JSON, statut retourné, contenu de la réponse, etc.
    @Autowired
    private MockMvc mockMvc;

    // Remplace le bean Spring DemoItemService par un mock Mockito
    // dans le contexte Spring utilisé pour ce test.
    // Le contrôleur sera donc testé avec un faux service,
    // ce qui permet de tester uniquement la couche web : routes,
    // statuts HTTP, JSON, validation, gestion des erreurs, etc.
    @MockitoBean
    private DemoItemService service;

    @Test
    void shouldReturnCreated_whenPostBodyIsValid() throws Exception {
        // Arrange
        when(service.create("demo")).thenReturn(new DemoItem(1L, "demo"));

        // Act + Assert : MockMvc exécute la requête et vérifie la réponse HTTP.
        mockMvc.perform(post("/api/demo-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"label\":\"demo\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/demo-items/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.label").value("demo"));

        verify(service).create("demo");
    }

    @Test
    void shouldReturnBadRequest_whenPostBodyIsInvalid() throws Exception {
        // Act + Assert : @Valid + @NotBlank provoquent une erreur 400.
        mockMvc.perform(post("/api/demo-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"label\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Le libellé est obligatoire"));

        verify(service, never()).create(org.mockito.ArgumentMatchers.anyString());
    }

    @Test
    void shouldReturnOk_whenItemExists() throws Exception {
        // Arrange
        when(service.getById(1L)).thenReturn(new DemoItem(1L, "demo"));

        // Act + Assert
        mockMvc.perform(get("/api/demo-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.label").value("demo"));

        verify(service).getById(1L);
    }

    @Test
    void shouldReturnNotFound_whenItemDoesNotExist() throws Exception {
        // Arrange
        when(service.getById(99L)).thenThrow(new DemoItemNotFoundException(99L));

        // Act + Assert : @RestControllerAdvice transforme l'exception en réponse HTTP 404.
        mockMvc.perform(get("/api/demo-items/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Aucun élément trouvé avec l'identifiant 99"));

        verify(service).getById(99L);
    }
}

/*
 * MockMvc ET mockMvc.perform(...)
 *
 * MockMvc est l'objet fourni par Spring Test pour simuler des requêtes HTTP
 * vers les contrôleurs Spring, sans démarrer un vrai serveur web.
 *
 * La méthode mockMvc.perform(...) signifie :
 * "exécute une requête HTTP simulée vers l'application".
 *
 * Dans le perform(...), on indique le type de requête à envoyer :
 *
 * - get("/url")    : simule une requête HTTP GET
 * - post("/url")   : simule une requête HTTP POST
 * - put("/url")    : simule une requête HTTP PUT
 * - delete("/url") : simule une requête HTTP DELETE
 *
 * On peut aussi préciser des informations supplémentaires sur la requête :
 *
 * - .contentType(MediaType.APPLICATION_JSON)
 *   indique que le corps envoyé est au format JSON.
 *
 * - .content("...")
 *   permet d'envoyer un corps de requête, par exemple un JSON dans un POST ou un PUT.
 *
 * - .param("nom", "valeur")
 *   permet d'envoyer des paramètres de requête.
 *
 * - .header("nom", "valeur")
 *   permet d'ajouter un header HTTP.
 *
 * Après le perform(...), on utilise généralement .andExpect(...)
 * pour vérifier la réponse HTTP obtenue.
 *
 * Exemples de vérifications possibles :
 *
 * - .andExpect(status().isOk())
 *   vérifie que le statut HTTP est 200.
 *
 * - .andExpect(status().isCreated())
 *   vérifie que le statut HTTP est 201.
 *
 * - .andExpect(status().isBadRequest())
 *   vérifie que le statut HTTP est 400.
 *
 * - .andExpect(status().isNotFound())
 *   vérifie que le statut HTTP est 404.
 *
 * - .andExpect(jsonPath("$.name").value("Exemple"))
 *   vérifie une valeur précise dans le JSON retourné.
 *
 * - .andExpect(jsonPath("$").isArray())
 *   vérifie que la réponse JSON est un tableau.
 *
 * - .andExpect(jsonPath("$.length()").value(2))
 *   vérifie la taille d'un tableau JSON.
 *
 * En résumé :
 *
 * mockMvc.perform(...) prépare et exécute une fausse requête HTTP.
 * andExpect(...) vérifie que la réponse HTTP est celle attendue.
 *
 * Dans cette classe de test, le service est mocké avec @MockitoBean.
 * Cela signifie que la requête HTTP arrive bien dans le contrôleur,
 * mais que le contrôleur utilise un faux service dont le comportement
 * est défini dans le test avec when(...).thenReturn(...).
 *
 * On teste donc principalement la couche web :
 * - l'URL appelée ;
 * - la méthode HTTP utilisée ;
 * - le statut HTTP retourné ;
 * - le JSON envoyé ou reçu ;
 * - la validation des données ;
 * - la transformation des erreurs en réponses HTTP.
 */
