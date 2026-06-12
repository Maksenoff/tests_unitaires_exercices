package com.example;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
// Indique à JUnit Platform que cette classe est une suite de tests.
// Elle ne contient pas directement de méthode de test.
// Elle sert à configurer le lancement de Cucumber.

@IncludeEngines("cucumber")
// Demande à JUnit Platform d'utiliser le moteur Cucumber.
// Sans cette annotation, JUnit ne sait pas forcément qu'il doit exécuter les fichiers .feature.

@SelectClasspathResource("features")
// Indique où se trouvent les fichiers .feature.
// Ici, Cucumber cherche dans src/test/resources/features.
// Au moment de l'exécution Maven, ce dossier est copié dans target/test-classes/features.

@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example")
// Indique le package contenant les step definitions.
// Cucumber cherche les méthodes annotées avec @Given, @When, @Then dans ce package.

@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-report.html, json:target/cucumber-report.json"
)
// Configure les rapports Cucumber.
// pretty : affiche une sortie lisible dans la console.
// html:target/cucumber-report.html : génère un rapport HTML.
// json:target/cucumber-report.json : génère un rapport JSON exploitable par d'autres outils.

public class RunCucumberTest {
}
