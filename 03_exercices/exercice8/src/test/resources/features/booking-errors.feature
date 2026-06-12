Feature: Booking errors

  Scenario: Unknown room cannot be booked
    Given room "Z999" does not exist
    When user "alice@example.com" books room "Z999" for 3 attendees from "2026-06-10T09:00" to "2026-06-10T10:00"
    Then a booking error should be thrown with message "Room not found"
    And the booking receipt should be empty
    And the room repository should not have saved the booking
    And no confirmation should have been sent

  Scenario: Room capacity cannot be exceeded
    Given a room "A101" named "Atlas" with capacity 8
    And room "A101" has no existing booking
    When user "alice@example.com" books room "A101" for 12 attendees from "2026-06-10T09:00" to "2026-06-10T10:00"
    Then a booking error should be thrown with message "Room capacity exceeded"
    And the booking receipt should be empty
    And the room repository should not have saved the booking
    And no confirmation should have been sent

  Scenario: End date must be after start date
    Given a room "A101" named "Atlas" with capacity 8
    And room "A101" has no existing booking
    When user "alice@example.com" books room "A101" for 4 attendees from "2026-06-10T10:00" to "2026-06-10T09:00"
    Then a booking error should be thrown with message "End date must be after start date"
    And the booking receipt should be empty
    And the room repository should not have saved the booking
    And no confirmation should have been sent