Feature: Calculator

  Scenario: Add two positive numbers
    Given a calculator
    When I add 2 and 3
    Then the result should be 5

  Scenario: Subtract two numbers
    Given a calculator
    When I subtract 10 and 4
    Then the result should be 6