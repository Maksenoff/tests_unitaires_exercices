package com.example.demoapi.bdd;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Classe de lancement des scénarios Cucumber avec JUnit Platform.
 *
 * @Suite indique que cette classe sert de suite de tests.
 * @IncludeEngines("cucumber") demande à JUnit d'utiliser le moteur Cucumber.
 * @SelectClasspathResource("features") indique où se trouvent les fichiers .feature.
 * GLUE_PROPERTY_NAME indique le package contenant les step definitions.
 * PLUGIN_PROPERTY_NAME configure l'affichage console et les rapports Cucumber.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.demoapi.bdd")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report.html, json:target/cucumber-report.json")
class RunCucumberTest {
}


// Classe de lancement des tests Cucumber.
// Elle indique à JUnit où trouver les fichiers .feature,
// quel moteur utiliser, où chercher les step definitions,
// et quels rapports générer.