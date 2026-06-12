package com.example.demoapi.repository;

import com.example.demoapi.model.DemoItem;

import java.util.List;
import java.util.Optional;

/**
 * Contrat de persistance.
 *
 * Dans une vraie application, cette interface pourrait être remplacée par un Repository Spring Data JPA.
 * Ici, elle existe surtout pour montrer comment isoler le service dans les tests unitaires avec Mockito.
 */
public interface DemoItemRepository {

    DemoItem save(String label);

    Optional<DemoItem> findById(Long id);

    List<DemoItem> findAll();

    void deleteAll();
}
