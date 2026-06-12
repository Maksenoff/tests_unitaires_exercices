Feature: Navigation par catégorie
  En tant qu'utilisateur, je veux naviguer par catégorie de produits pour découvrir ce qui est disponible.

  Scenario: Sélection d'une catégorie
    Given des produits de la catégorie "sport" sont disponibles
    When je sélectionne la catégorie "sport"
    Then je vois uniquement des produits de la catégorie "sport"
