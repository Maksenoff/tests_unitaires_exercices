package com.example.demoapi.model;

/**
 * Modèle très simple utilisé par la démo.
 *
 * Ici, on utilise un record Java pour éviter du code inutile :
 * constructeur, getters, equals, hashCode et toString sont générés automatiquement.
 */
public record DemoItem(Long id, String label) {
}
