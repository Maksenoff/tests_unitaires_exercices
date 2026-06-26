Feature: Gestion des comptes bancaires

  Scenario: Création d'un nouveau compte
    Given aucun compte n'existe
    When je crée un compte avec le numéro "FR001" au nom de "Alice"
    Then la réponse HTTP doit être 201
    And le solde du compte "FR001" doit être 0 euros

  Scenario: Refus de création si le numéro existe déjà
    Given un compte "FR001" au nom de "Alice" existe avec un solde de 0 euros
    When je crée un compte avec le numéro "FR001" au nom de "Alice"
    Then la réponse HTTP doit être 409

  Scenario: Dépôt d'argent sur un compte
    Given un compte "FR001" au nom de "Alice" existe avec un solde de 0 euros
    When je dépose 500 euros sur le compte "FR001"
    Then la réponse HTTP doit être 200
    And le solde du compte "FR001" doit être 500 euros

  Scenario: Retrait avec fonds suffisants
    Given un compte "FR001" au nom de "Alice" existe avec un solde de 500 euros
    When je retire 200 euros du compte "FR001"
    Then la réponse HTTP doit être 200
    And le solde du compte "FR001" doit être 300 euros

  Scenario: Retrait avec fonds insuffisants
    Given un compte "FR001" au nom de "Alice" existe avec un solde de 50 euros
    When je retire 200 euros du compte "FR001"
    Then la réponse HTTP doit être 422

  Scenario: Virement entre deux comptes
    Given un compte "FR001" au nom de "Alice" existe avec un solde de 500 euros
    And un second compte "FR002" au nom de "Bob" existe
    When j'effectue un virement de 200 euros du compte "FR001" vers le compte "FR002"
    Then la réponse HTTP doit être 200

  Scenario: Virement refusé pour solde insuffisant
    Given un compte "FR001" au nom de "Alice" existe avec un solde de 50 euros
    And un second compte "FR002" au nom de "Bob" existe
    When j'effectue un virement de 200 euros du compte "FR001" vers le compte "FR002"
    Then la réponse HTTP doit être 422
