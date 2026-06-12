Feature: Booking acceptance

  Scenario: Book an available room
    Given a room "A101" named "Atlas" with capacity 8
    And room "A101" has no existing booking
    When user "alice@example.com" books room "A101" for 4 attendees from "2026-06-10T09:00" to "2026-06-10T10:00"
    Then the booking receipt should not be empty
    And the booking message should be "Booking confirmed"
    And the booking receipt room should be "A101"
    And the room repository should have saved the booking
    And a confirmation should have been sent to "alice@example.com"

  Scenario: Book a room at maximum capacity
    Given a room "B202" named "Boreal" with capacity 6
    And room "B202" has no existing booking
    When user "bob@example.com" books room "B202" for 6 attendees from "2026-06-10T11:00" to "2026-06-10T12:00"
    Then the booking receipt should not be empty
    And the booking message should be "Booking confirmed"
    And the room repository should have saved the booking
    And a confirmation should have been sent to "bob@example.com"