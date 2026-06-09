Feature: Réservation refusée

  Scenario: Réservation refusée si la salle est inconnue
    Given aucune salle avec le code "SALLE-Z"
    When je réserve "SALLE-Z" pour "user@example.com" avec 5 participants du "2024-06-10T09:00" au "2024-06-10T11:00"
    Then la réservation est refusée avec le message "Salle inconnue"

  Scenario: Réservation refusée si la capacité est insuffisante
    Given une salle "SALLE-A" nommée "Salle Apollo" avec une capacité de 5
    And aucune réservation existante pour "SALLE-A"
    When je réserve "SALLE-A" pour "user@example.com" avec 10 participants du "2024-06-10T09:00" au "2024-06-10T11:00"
    Then la réservation est refusée avec le message "Capacité insuffisante"

  Scenario: Réservation refusée si la période est invalide
    Given une salle "SALLE-A" nommée "Salle Apollo" avec une capacité de 10
    And aucune réservation existante pour "SALLE-A"
    When je réserve "SALLE-A" pour "user@example.com" avec 5 participants du "2024-06-10T11:00" au "2024-06-10T09:00"
    Then la réservation est refusée avec le message "Période invalide"

  Scenario: Réservation refusée si conflit de réservation
    Given une salle "SALLE-A" nommée "Salle Apollo" avec une capacité de 10
    And une réservation existante pour "SALLE-A" du "2024-06-10T09:00" au "2024-06-10T11:00"
    When je réserve "SALLE-A" pour "user@example.com" avec 5 participants du "2024-06-10T10:00" au "2024-06-10T12:00"
    Then la réservation est refusée avec le message "Créneau indisponible"
