package com.example.demoapi.controller;

import com.example.demoapi.model.CreateDemoItemRequest;
import com.example.demoapi.model.DemoItemResponse;
import com.example.demoapi.service.DemoItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * Couche web REST.
 *
 * @RestController indique que les méthodes renvoient directement des réponses HTTP/JSON.
 * @RequestMapping définit le préfixe commun des URLs de ce contrôleur.
 */
@RestController
@RequestMapping("/api/demo-items")
public class DemoItemController {

    private final DemoItemService service;

    public DemoItemController(DemoItemService service) {
        this.service = service;
    }

    /**
     * POST /api/demo-items
     *
     * @RequestBody lit le JSON envoyé par le client.
     * @Valid déclenche la validation des annotations présentes dans CreateDemoItemRequest.
     */
    @PostMapping
    public ResponseEntity<DemoItemResponse> create(@Valid @RequestBody CreateDemoItemRequest request) {
        var createdItem = service.create(request.label());
        var response = DemoItemResponse.from(createdItem);

        return ResponseEntity
                .created(URI.create("/api/demo-items/" + response.id()))
                .body(response);
    }

    /**
     * GET /api/demo-items/{id}
     *
     * @PathVariable récupère la valeur dynamique présente dans l'URL.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemoItemResponse> getById(@PathVariable Long id) {
        var item = service.getById(id);
        return ResponseEntity.ok(DemoItemResponse.from(item));
    }

    /**
     * GET /api/demo-items
     *
     * Retourne tous les éléments. Utile pour montrer un test HTTP simple en intégration.
     */
    @GetMapping
    public ResponseEntity<List<DemoItemResponse>> findAll() {
        var responses = service.findAll()
                .stream()
                .map(DemoItemResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    /**
     * DELETE /api/demo-items
     *
     * Endpoint pratique pour remettre l'API à zéro dans la démo et les scénarios BDD.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
