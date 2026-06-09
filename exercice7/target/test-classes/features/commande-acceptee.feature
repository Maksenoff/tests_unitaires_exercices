Feature: Commande acceptée avec remise selon profil client

  Scenario: Commande acceptée pour un client STANDARD
    Given un produit "PROD-001" nommé "Clavier" à 50.0 euros avec un stock de 10
    When un client STANDARD commande 2 "PROD-001"
    Then la commande est acceptée
    And le montant total est 100.0 euros
    And le message de confirmation est "Commande acceptée"

  Scenario: Commande acceptée pour un client PREMIUM
    Given un produit "PROD-001" nommé "Clavier" à 50.0 euros avec un stock de 10
    When un client PREMIUM commande 2 "PROD-001"
    Then la commande est acceptée
    And le montant total est 90.0 euros
    And le message de confirmation est "Commande acceptée"

  Scenario: Commande acceptée pour un client VIP
    Given un produit "PROD-001" nommé "Clavier" à 50.0 euros avec un stock de 10
    When un client VIP commande 2 "PROD-001"
    Then la commande est acceptée
    And le montant total est 80.0 euros
    And le message de confirmation est "Commande acceptée"
