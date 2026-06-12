Feature: Connexion
  En tant qu'utilisateur, je veux me connecter à mon compte pour accéder à l'application.

  Scenario: Connexion réussie
    Given un utilisateur "alice" avec le mot de passe "pass123" existe
    When je me connecte avec le nom "alice" et le mot de passe "pass123"
    Then je suis redirigé vers la page d'accueil

  Scenario: Connexion avec un mauvais mot de passe
    Given un utilisateur "alice" avec le mot de passe "pass123" existe
    When je me connecte avec le nom "alice" et le mot de passe "mauvais"
    Then je vois le message d'erreur de connexion "Identifiants invalides"
