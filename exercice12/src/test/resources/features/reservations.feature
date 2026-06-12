Feature: Gestion des réservations de salles de réunion

  Scenario: Réservation acceptée quand la salle existe et que le créneau est libre
    Given une salle "Salle A" de capacité 10 existe
    When je crée une réservation pour cette salle au nom de "Alice" du "2026-07-01T09:00" au "2026-07-01T10:00"
    Then la réponse HTTP doit être 201

  Scenario: Réservation refusée quand la salle n'existe pas
    Given aucune salle n'existe
    When je tente de créer une réservation pour la salle avec l'identifiant 999
    Then la réponse HTTP doit être 404

  Scenario: Réservation refusée quand le créneau chevauche une réservation existante
    Given une salle "Salle B" de capacité 5 existe
    And une réservation existe pour cette salle de "2026-07-01T09:00" à "2026-07-01T11:00"
    When je tente de créer une réservation pour cette salle de "2026-07-01T10:00" à "2026-07-01T12:00"
    Then la réponse HTTP doit être 409
