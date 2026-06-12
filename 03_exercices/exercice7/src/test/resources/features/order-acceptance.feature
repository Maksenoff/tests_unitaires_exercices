Feature: Order acceptance

  Scenario: Standard customer places an order without discount
    Given a product "BOOK-001" named "Java Book" costs 50.00 with stock 10
    When customer "standard@example.com" with status STANDARD orders 2 products "BOOK-001"
    Then the order receipt should not be empty
    And the order total should be 100.00
    And the confirmation message should be "Order accepted for standard@example.com"
    And the product catalog should have searched product "BOOK-001"

  Scenario: Premium customer places an order with 10 percent discount
    Given a product "BOOK-001" named "Java Book" costs 50.00 with stock 10
    When customer "premium@example.com" with status PREMIUM orders 2 products "BOOK-001"
    Then the order receipt should not be empty
    And the order total should be 90.00
    And the confirmation message should be "Order accepted for premium@example.com"
    And the product catalog should have searched product "BOOK-001"

  Scenario: VIP customer places an order with 20 percent discount
    Given a product "BOOK-001" named "Java Book" costs 50.00 with stock 10
    When customer "vip@example.com" with status VIP orders 2 products "BOOK-001"
    Then the order receipt should not be empty
    And the order total should be 80.00
    And the confirmation message should be "Order accepted for vip@example.com"
    And the product catalog should have searched product "BOOK-001"