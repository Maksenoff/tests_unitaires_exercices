Feature: Recherche de produits
  En tant qu'utilisateur, je veux rechercher des produits pour trouver rapidement ce dont j'ai besoin.

  Scenario: Recherche par mot-clé
    Given des produits contenant "chaussure" sont disponibles dans le catalogue
    When je recherche le mot-clé "chaussure"
    Then les résultats contiennent des produits avec "chaussure" dans le nom

  Scenario: Recherche par prix maximum
    Given des produits à différents prix sont disponibles dans le catalogue
    When je recherche des produits avec un prix maximum de 50.0
    Then tous les produits des résultats ont un prix inférieur ou égal à 50.0
