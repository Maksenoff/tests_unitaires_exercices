Feature: Réservation acceptée

  Scenario: Réservation acceptée dans les conditions normales
    Given une salle "SALLE-A" nommée "Salle Apollo" avec une capacité de 10
    And aucune réservation existante pour "SALLE-A"
    When je réserve "SALLE-A" pour "user@example.com" avec 5 participants du "2024-06-10T09:00" au "2024-06-10T11:00"
    Then la réservation est acceptée
    And le message de confirmation est "Réservation confirmée"

  Scenario: Réservation acceptée à capacité maximale
    Given une salle "SALLE-A" nommée "Salle Apollo" avec une capacité de 10
    And aucune réservation existante pour "SALLE-A"
    When je réserve "SALLE-A" pour "user@example.com" avec 10 participants du "2024-06-10T09:00" au "2024-06-10T11:00"
    Then la réservation est acceptée

  Scenario: Réservation acceptée si le créneau commence après une réservation existante
    Given une salle "SALLE-A" nommée "Salle Apollo" avec une capacité de 10
    And une réservation existante pour "SALLE-A" du "2024-06-10T09:00" au "2024-06-10T11:00"
    When je réserve "SALLE-A" pour "user@example.com" avec 5 participants du "2024-06-10T11:00" au "2024-06-10T13:00"
    Then la réservation est acceptée
