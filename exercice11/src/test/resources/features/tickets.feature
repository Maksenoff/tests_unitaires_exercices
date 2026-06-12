Feature: Gestion des tickets de support

  Scenario: Création d'un ticket valide
    Given aucun ticket n'existe
    When je crée un ticket avec le titre "Problème de connexion" et la priorité "HIGH"
    Then la réponse HTTP doit être 201
    And le ticket est créé avec le statut "OPEN"

  Scenario: Résolution d'un ticket
    Given un ticket existe avec le titre "Bug critique" et la priorité "HIGH"
    When je modifie le statut du ticket vers "RESOLVED"
    Then la réponse HTTP doit être 200
    And le ticket a le statut "RESOLVED"

  Scenario: Refus de modification d'un ticket déjà résolu
    Given un ticket résolu existe
    When je tente de modifier son statut vers "IN_PROGRESS"
    Then la réponse HTTP doit être 409

  Scenario: Consultation d'un ticket inexistant
    Given aucun ticket n'existe
    When je consulte le ticket avec l'identifiant 999
    Then la réponse HTTP doit être 404
