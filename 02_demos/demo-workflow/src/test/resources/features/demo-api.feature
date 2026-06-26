Feature: API de demonstration Spring
  Cette feature montre comment écrire des scénarios BDD sur une API REST Spring.
  Le vocabulaire reste volontairement simple pour se concentrer sur Given / When / Then.

  Scenario: Creation valide d un element
    Given aucun element n existe dans l API
    When je cree un element avec le libelle "demo bdd"
    Then la reponse HTTP doit etre 201
    And la reponse contient le libelle "demo bdd"

  Scenario: Recuperation d un element cree
    Given aucun element n existe dans l API
    When je cree un element avec le libelle "element a retrouver"
    And je demande l element cree
    Then la reponse HTTP doit etre 200
    And la reponse contient le libelle "element a retrouver"

  Scenario: Refus d une creation invalide
    Given aucun element n existe dans l API
    When je cree un element avec le libelle ""
    Then la reponse HTTP doit etre 400
    And la reponse contient un message d erreur

  Scenario: Element introuvable
    Given aucun element n existe dans l API
    When je demande l element avec l identifiant 99
    Then la reponse HTTP doit etre 404
    And la reponse contient un message d erreur

  Scenario Outline: Creation d un element avec plusieurs jeux de donnees
    Given aucun element n existe dans l API
    When je cree un element avec le libelle "<label>"
    Then la reponse HTTP doit etre <status>

    Examples:
      | label              | status |
      | element valide     | 201    |
      |                    | 400    |

  Scenario: Recuperation d une liste d elements prepares avec une table
    Given aucun element n existe dans l API
    And les elements suivants existent dans l API
      | label        |
      | premier      |
      | deuxieme     |
      | troisieme    |
    When je demande la liste des elements
    Then la reponse HTTP doit etre 200
    And la reponse contient 3 elements
    And la reponse contient au moins le libelle "premier"
    And la reponse contient au moins le libelle "deuxieme"
    And la reponse contient au moins le libelle "troisieme"