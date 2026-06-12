Feature: Ajout de produit à une commande
  En tant qu'utilisateur, je veux ajouter des produits à ma commande.

  Scenario: Ajout d'un nouveau produit dans la commande
    Given une commande avec l'identifiant 1 existe
    And un produit "chaussures" avec l'identifiant 10 est disponible
    When j'ajoute le produit 10 à la commande 1
    Then le produit "chaussures" est dans la commande avec une quantité de 1

  Scenario: Ajout d'un produit déjà présent augmente la quantité
    Given une commande avec l'identifiant 1 contenant déjà le produit 10 en quantité 1
    When j'ajoute le produit 10 à la commande 1
    Then le produit avec l'identifiant 10 est dans la commande avec une quantité de 2

  Scenario: Ajout sur une commande inexistante renvoie une erreur
    Given aucune commande avec l'identifiant 99
    When j'ajoute le produit 10 à la commande 99
    Then une erreur de commande "Commande introuvable" est renvoyée
