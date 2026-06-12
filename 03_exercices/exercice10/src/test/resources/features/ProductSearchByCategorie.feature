Feature: Product Search By Categorie
  En tant qu'utilisateur, je veux naviguer par catégorie de produits pour découvrir ce qui est disponible.

  Scenario: Search by Category
    Given User want to search a product by category
    When A user send the category for the research
    And the app search for all the related product by category
    Then the app return the products find for the category