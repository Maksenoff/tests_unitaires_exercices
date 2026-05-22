package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RechercheVilleTest {

    private RechercheVille rechercheVille;

    @BeforeEach
    void setUp() {
        rechercheVille = new RechercheVille();
    }

    @Test
    void rechercheMoinsDe2Caracteres_leveUneException() {
        assertThrows(NotFoundException.class, () -> rechercheVille.Rechercher("P"));
    }

    @Test
    void rechercheNull_leveUneException() {
        assertThrows(NotFoundException.class, () -> rechercheVille.Rechercher(null));
    }

    @Test
    void rechercheVaRetourneValenceEtVancouver() {
        List<String> resultats = rechercheVille.Rechercher("Va");
        assertTrue(resultats.contains("Valence"));
        assertTrue(resultats.contains("Vancouver"));
        assertEquals(2, resultats.size());
    }

    @Test
    void rechercheInsensibleCasse() {
        List<String> resultats = rechercheVille.Rechercher("pa");
        assertTrue(resultats.contains("Paris"));
    }

    @Test
    void recherchePartielle_apeRetourneBudapest() {
        List<String> resultats = rechercheVille.Rechercher("ape");
        assertTrue(resultats.contains("Budapest"));
    }

    @Test
    void rechercheEtoile_retourneToutesLesVilles() {
        List<String> resultats = rechercheVille.Rechercher("*");
        assertEquals(16, resultats.size());
    }

    @Test
    void rechercheAucunResultat_retourneListeVide() {
        List<String> resultats = rechercheVille.Rechercher("xyz");
        assertTrue(resultats.isEmpty());
    }

    @Test
    void rechercheParis_retourneParis() {
        List<String> resultats = rechercheVille.Rechercher("Paris");
        assertEquals(1, resultats.size());
        assertEquals("Paris", resultats.get(0));
    }
}
