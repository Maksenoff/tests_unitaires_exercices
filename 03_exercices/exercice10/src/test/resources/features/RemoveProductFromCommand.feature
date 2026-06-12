Feature: Remove product from Command

  En tant qu'utilisateur, je veux supprimer des produits de la commande.

  Scenario: Remove Product
    Given User want remove Product from a Command
    When A app remove product to a command
    And the app save the modify command
    Then the app return modify command


  Scenario: Reduce quantity By 1
    Given User want reduce the quantity of a Product from a Command
    When A app reduce the quantity of the product to a command
    And the app save the modify command
    Then the app return modify command with less of the product

  Scenario: Error if product is not in the command
    Given User want remove Product from a Command
    Then the app return a error NotFoundException