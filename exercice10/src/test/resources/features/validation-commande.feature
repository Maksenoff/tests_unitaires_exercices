Feature: Validation de commande
  En tant qu'utilisateur, je veux valider une commande.

  Scenario: Validation réussie d'une commande
    Given une commande avec l'identifiant 1 existe
    When je valide la commande 1
    Then la commande est confirmée
    And je reçois un message de confirmation de commande "Commande validée avec succès"

  Scenario: Validation d'une commande inexistante
    Given aucune commande avec l'identifiant 99
    When je valide la commande 99
    Then une erreur de commande "Commande introuvable" est renvoyée
