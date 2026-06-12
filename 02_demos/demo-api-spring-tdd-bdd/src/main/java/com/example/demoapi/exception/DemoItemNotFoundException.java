package com.example.demoapi.exception;

/**
 * Exception métier simple.
 *
 * Elle permet de distinguer clairement un cas d'erreur fonctionnel :
 * l'utilisateur demande un élément qui n'existe pas.
 */
public class DemoItemNotFoundException extends RuntimeException {

    public DemoItemNotFoundException(Long id) {
        super("Aucun élément trouvé avec l'identifiant " + id);
    }
}
