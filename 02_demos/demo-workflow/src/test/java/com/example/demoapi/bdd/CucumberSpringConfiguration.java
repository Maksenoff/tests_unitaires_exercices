package com.example.demoapi.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Configuration Spring pour les scénarios BDD.
 *
 * @CucumberContextConfiguration relie Cucumber au contexte Spring.
 * @SpringBootTest démarre l'application complète pour tester le comportement réel.
 * @AutoConfigureMockMvc permet aux steps d'appeler l'API avec MockMvc.
 */
@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class CucumberSpringConfiguration {
}


// Cette classe permet de connecter Cucumber avec Spring Boot.
// Sans cette configuration, Cucumber exécuterait les steps comme de simples classes Java,
// mais il ne saurait pas forcément charger le contexte Spring de l'application.
//
// Grâce à @CucumberContextConfiguration et @SpringBootTest,
// les step definitions peuvent utiliser les vrais beans Spring avec @Autowired,
// comme MockMvc, le repository, ObjectMapper, etc.