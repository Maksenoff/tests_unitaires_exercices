Feature: Order errors

  Scenario: Unknown product cannot be ordered
    Given product "UNKNOWN-001" does not exist
    When customer "client@example.com" with status STANDARD orders 1 products "UNKNOWN-001"
    Then an order error should be thrown with message "Product not found"
    And the order receipt should be empty
    And the product catalog should have searched product "UNKNOWN-001"

  Scenario: Product with insufficient stock cannot be ordered
    Given a product "BOOK-001" named "Java Book" costs 50.00 with stock 2
    When customer "client@example.com" with status STANDARD orders 5 products "BOOK-001"
    Then an order error should be thrown with message "Insufficient stock"
    And the order receipt should be empty
    And the product catalog should have searched product "BOOK-001"