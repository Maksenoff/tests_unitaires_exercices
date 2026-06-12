
  Feature: Customer Register

    En tant qu'utilisateur, je veux créer un compte pour pouvoir passer des commandes.

    Scenario: Register a new user
      Given A new user want to create a account
      When A user send is "email@email.com" "Toto" and "Pa$$word"
      And the new user is add to the app
      Then there is a message who validate Register

    Scenario: Register a user already exist
      Given A new user want to create a account
      When A user send is "email@email.com" "Toto" and "Pa$$word"
      Then the new user is add to the app but user Already exist

