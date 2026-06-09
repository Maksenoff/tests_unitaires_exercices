Feature: Calculator errors

  Scenario: Divide by zero
    Given a calculator
    When I divide 10 by 0
    Then an error should be thrown with message "Division by zero is not allowed"