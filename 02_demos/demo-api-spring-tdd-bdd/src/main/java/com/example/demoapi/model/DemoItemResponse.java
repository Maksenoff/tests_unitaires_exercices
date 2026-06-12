package com.example.demoapi.model;

/**
 * Réponse JSON renvoyée par l'API.
 *
 * On sépare volontairement la request, le modèle interne et la response pour montrer
 * une structure propre, même dans une petite démo.
 */
public record DemoItemResponse(Long id, String label) {

    public static DemoItemResponse from(DemoItem item) {
        return new DemoItemResponse(item.id(), item.label());
    }
}
