Feature: Add product to command

  En tant qu'utilisateur, je veux ajouter des produits a la commande.

  Scenario: Add Product To Command
    Given User want add Product to a Command
    When A user add product to a command
    And the app save the command
    Then the app return command

  Scenario: increased quantity for a Product To Command
    Given User want add Product to a Command where the product is already in the command
    When A user add product to a command
    And the app save the command
    Then the app return command with 2 product
