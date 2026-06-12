package com.example.demoapi.service;

import com.example.demoapi.exception.DemoItemNotFoundException;
import com.example.demoapi.model.DemoItem;
import com.example.demoapi.repository.DemoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Couche service : elle porte les règles applicatives.
 *
 * @Service indique à Spring que cette classe est un composant métier.
 * C'est une bonne cible pour les tests unitaires en TDD :
 * on teste les règles sans démarrer toute l'application Spring.
 */
@Service
public class DemoItemService {

    private final DemoItemRepository repository;

    /**
     * Injection par constructeur.
     *
     * Avantage : la dépendance est obligatoire, claire et facile à remplacer par un mock dans les tests.
     */
    public DemoItemService(DemoItemRepository repository) {
        this.repository = repository;
    }

    public DemoItem create(String label) {
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Le libellé est obligatoire");
        }

        return repository.save(label.trim());
    }

    public DemoItem getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DemoItemNotFoundException(id));
    }

    public List<DemoItem> findAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
