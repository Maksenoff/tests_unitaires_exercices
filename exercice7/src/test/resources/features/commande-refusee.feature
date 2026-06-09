Feature: Commande refusée

  Scenario: Commande refusée si le produit est inconnu
    Given aucun produit avec la référence "PROD-999"
    When un client STANDARD commande 1 "PROD-999"
    Then la commande est refusée avec le message "Produit inconnu"

  Scenario: Commande refusée si le stock est insuffisant
    Given un produit "PROD-001" nommé "Clavier" à 50.0 euros avec un stock de 3
    When un client STANDARD commande 5 "PROD-001"
    Then la commande est refusée avec le message "Stock insuffisant"
