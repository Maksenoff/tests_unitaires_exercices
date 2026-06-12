package com.example.demoapi.integration;

import com.example.demoapi.repository.DemoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test d'intégration Spring Boot.
 *
 * @SpringBootTest démarre le contexte Spring complet.
 * @AutoConfigureMockMvc fournit MockMvc pour tester l'API sans serveur HTTP réel.
 *
 * Différence avec @WebMvcTest : ici on utilise le vrai contrôleur, le vrai service
 * et le vrai repository en mémoire.
 */
@SpringBootTest
@AutoConfigureMockMvc
class DemoItemApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoItemRepository repository;

    @BeforeEach
    void setUp() {
        // On remet l'état de l'application à zéro avant chaque test.
        repository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveItem_withRealSpringContext() throws Exception {
        // Act + Assert : création par appel HTTP simulé.
        mockMvc.perform(post("/api/demo-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"label\":\"demo integration\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.label").value("demo integration"));

        // Act + Assert : récupération par appel HTTP simulé.
        mockMvc.perform(get("/api/demo-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.label").value("demo integration"));
    }
}

/*
 * Dans ce test d'intégration, on utilise @SpringBootTest.
 *
 * Contrairement à @WebMvcTest, qui charge seulement la couche web,
 * @SpringBootTest charge le contexte Spring complet de l'application :
 * contrôleurs, services, repositories, configuration, validation, etc.
 *
 * L'objectif est donc de tester le fonctionnement global de l'application,
 * en vérifiant qu'une requête HTTP passe correctement à travers plusieurs couches.
 *
 * Avec @AutoConfigureMockMvc, Spring configure un objet MockMvc utilisable
 * dans le test.
 *
 * MockMvc permet toujours de simuler une requête HTTP, mais ici la requête
 * traverse les vrais composants Spring de l'application, et non un contrôleur
 * isolé avec des services mockés.
 *
 * Important :
 * l'application Spring est bien chargée pour le test, mais elle n'est pas
 * forcément démarrée sur un vrai port HTTP comme localhost:8080.
 * MockMvc envoie la requête directement dans le contexte de test Spring.
 */