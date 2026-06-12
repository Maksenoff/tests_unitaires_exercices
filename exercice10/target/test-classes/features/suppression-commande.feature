Feature: Suppression de produit d'une commande
  En tant qu'utilisateur, je veux supprimer des produits de ma commande.

  Scenario: Diminution de la quantité d'un produit
    Given une commande avec l'identifiant 1 contenant déjà le produit 10 en quantité 2
    When je supprime le produit 10 de la commande 1
    Then le produit avec l'identifiant 10 est dans la commande avec une quantité de 1

  Scenario: Suppression du produit quand la quantité est 1
    Given une commande avec l'identifiant 1 contenant déjà le produit 10 en quantité 1
    When je supprime le produit 10 de la commande 1
    Then le produit avec l'identifiant 10 n'est plus dans la commande 1

  Scenario: Suppression d'un produit absent de la commande
    Given une commande avec l'identifiant 1 existe
    When je supprime le produit 99 de la commande 1
    Then une erreur de commande "Produit absent de la commande" est renvoyée

  Scenario: Suppression sur une commande inexistante renvoie une erreur
    Given aucune commande avec l'identifiant 99
    When je supprime le produit 10 de la commande 99
    Then une erreur de commande "Commande introuvable" est renvoyée
