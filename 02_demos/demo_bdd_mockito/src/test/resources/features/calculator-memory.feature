Feature: Calculator memory

  Scenario: Check empty memory
    Given a calculator
    When I clear the calculator memory
    Then the calculator memory should be empty

  Scenario: Check saved memory
    Given a calculator
    When I save 42 in calculator memory
    Then the calculator memory should contain 42