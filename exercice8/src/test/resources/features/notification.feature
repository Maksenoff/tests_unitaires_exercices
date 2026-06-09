Feature: Notification de réservation

  Scenario: Notification envoyée en cas de succès
    Given une salle "SALLE-A" nommée "Salle Apollo" avec une capacité de 10
    And aucune réservation existante pour "SALLE-A"
    When je réserve "SALLE-A" pour "user@example.com" avec 5 participants du "2024-06-10T09:00" au "2024-06-10T11:00"
    Then la notification de confirmation est envoyée

  Scenario: Notification non envoyée en cas d'échec
    Given aucune salle avec le code "SALLE-Z"
    When je réserve "SALLE-Z" pour "user@example.com" avec 5 participants du "2024-06-10T09:00" au "2024-06-10T11:00"
    Then la notification de confirmation n'est pas envoyée
