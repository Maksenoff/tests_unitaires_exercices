package com.example.demoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot.
 *
 * @SpringBootApplication combine notamment :
 * - @Configuration : la classe peut déclarer de la configuration Spring ;
 * - @EnableAutoConfiguration : Spring Boot configure automatiquement l'application ;
 * - @ComponentScan : Spring détecte les @Controller, @Service, @Repository, etc.
 */
@SpringBootApplication
public class DemoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiApplication.class, args);
    }
}
