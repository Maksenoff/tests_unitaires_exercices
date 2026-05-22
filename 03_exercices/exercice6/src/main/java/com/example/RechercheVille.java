package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RechercheVille {

    private List<String> villes;

    public RechercheVille() {
        villes = Arrays.asList(
            "Paris", "Budapest", "Skopje", "Rotterdam", "Valence",
            "Vancouver", "Amsterdam", "Vienne", "Sydney", "New York",
            "Londres", "Bangkok", "Hong Kong", "Dubaï", "Rome", "Istanbul"
        );
    }

    public List<String> Rechercher(String mot) {
        if (mot != null && mot.equals("*")) {
            return new ArrayList<>(villes);
        }

        if (mot == null || mot.length() < 2) {
            throw new NotFoundException("Recherche trop courte");
        }

        List<String> resultats = new ArrayList<>();
        String motLower = mot.toLowerCase();

        for (String ville : villes) {
            if (ville.toLowerCase().contains(motLower)) {
                resultats.add(ville);
            }
        }

        return resultats;
    }
}
