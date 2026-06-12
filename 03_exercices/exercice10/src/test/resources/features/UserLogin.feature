Feature: Customer Login

  En tant qu'utilisateur, je veux me connecter à mon compte pour accéder et passer des commandes.

  Scenario: Login a user
    Given A User try to connect
    When A user send is "root" and "Root"
    And the user is log to the app
    Then there is a message who validate Connection

  Scenario: Login a user who not exist
    Given A User try to connect
    When A user send is "root" and "Root"
    Then the user does not exist there is a error

  Scenario: Login a user with wrong password
    Given A User try to connect
    When A user send is "root" and "Root"
    Then wrong password for the user there is a error

