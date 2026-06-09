Feature: Calculator operations

  Scenario: Add two positive numbers
    Given a calculator
    When I add 2 and 3
    Then the result should be 5

  Scenario: Subtract two numbers
    Given a calculator
    When I subtract 10 and 4
    Then the result should be 6

  Scenario: Multiply two numbers
    Given a calculator
    When I multiply 4 and 5
    Then the result should be 20

  Scenario: Divide two numbers
    Given a calculator
    When I divide 20 by 4
    Then the result should be 5

  Scenario: Check if a number is even
    Given a calculator
    When I check if 8 is even
    Then the boolean result should be true

  Scenario: Check if a number is positive
    Given a calculator
    When I check if 12 is positive
    Then the boolean result should be true

  Scenario: Create a range of numbers
    Given a calculator
    When I create a range from 1 to 5
    Then the range should contain 1, 2, 3, 4 and 5