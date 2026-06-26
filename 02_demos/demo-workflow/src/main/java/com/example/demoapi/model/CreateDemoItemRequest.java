package com.example.demoapi.model;

import jakarta.validation.constraints.NotBlank;

/**
 * Corps JSON attendu lors de la création d'un élément.
 *
 * @NotBlank impose que le champ ne soit ni null, ni vide, ni composé uniquement d'espaces.
 * Cette annotation sera déclenchée dans le contrôleur grâce à @Valid.
 */
public record CreateDemoItemRequest(
        @NotBlank(message = "Le libellé est obligatoire")
        String label
) {
}
