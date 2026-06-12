Feature: Création de compte
  En tant qu'utilisateur, je veux créer un compte pour pouvoir passer des commandes.

  Scenario: Inscription réussie
    Given je suis sur le formulaire d'inscription
    When je m'inscris avec l'email "alice@example.com", le nom "alice" et le mot de passe "pass123"
    Then mon compte est créé avec succès
    And je reçois le message de confirmation "Compte créé avec succès"

  Scenario: Inscription avec un identifiant déjà existant
    Given un utilisateur "alice" est déjà inscrit
    When je m'inscris avec l'email "alice@example.com", le nom "alice" et le mot de passe "pass123"
    Then l'inscription échoue avec le message "Identifiant déjà utilisé"
