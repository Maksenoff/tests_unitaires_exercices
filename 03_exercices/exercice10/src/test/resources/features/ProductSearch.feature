Feature: Product Search
  En tant qu'utilisateur, je veux rechercher des produits pour trouver ce dont j'ai besoin rapidement.

  Scenario: Search by name
    Given User want to search a product
    When A user send the name or a part of the name of the product he search
    And the app search for all the related product by name
    Then the app return the products find

  Scenario: Search by price
    Given User want to search a product
    When A user send the price of the product he search
    And the app search for all the related product by price
    Then the app return the products find