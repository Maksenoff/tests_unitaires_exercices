Feature: Customer discount

  Scenario: Unknown customer has no discount
    Given an unknown customer with email "unknown@example.com"
    When I calculate the discount rate for "unknown@example.com"
    Then the discount rate should be 0
    And the customer repository should have checked if "unknown@example.com" exists

  Scenario: Regular customer has no discount
    Given a known customer with email "regular@example.com" and 2 orders
    When I calculate the discount rate for "regular@example.com"
    Then the discount rate should be 0
    And the customer repository should have counted orders for "regular@example.com"

  Scenario: Loyal customer has a 10 percent discount
    Given a known customer with email "loyal@example.com" and 5 orders
    When I calculate the discount rate for "loyal@example.com"
    Then the discount rate should be 10
    And the customer repository should have counted orders for "loyal@example.com"

  Scenario: Very loyal customer has a 20 percent discount
    Given a known customer with email "vip@example.com" and 12 orders
    When I calculate the discount rate for "vip@example.com"
    Then the discount rate should be 20
    And the customer repository should have counted orders for "vip@example.com"