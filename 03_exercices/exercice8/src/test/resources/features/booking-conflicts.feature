Feature: Booking conflicts

  Scenario: Room cannot be booked when another booking overlaps
    Given a room "A101" named "Atlas" with capacity 8
    And room "A101" already has a booking from "2026-06-10T09:00" to "2026-06-10T10:00"
    When user "charlie@example.com" books room "A101" for 3 attendees from "2026-06-10T09:30" to "2026-06-10T10:30"
    Then a booking error should be thrown with message "Room already booked"
    And the booking receipt should be empty
    And the room repository should not have saved the booking
    And no confirmation should have been sent

  Scenario: Room can be booked just after another booking
    Given a room "A101" named "Atlas" with capacity 8
    And room "A101" already has a booking from "2026-06-10T09:00" to "2026-06-10T10:00"
    When user "diane@example.com" books room "A101" for 3 attendees from "2026-06-10T10:00" to "2026-06-10T11:00"
    Then the booking receipt should not be empty
    And the booking message should be "Booking confirmed"
    And the room repository should have saved the booking
    And a confirmation should have been sent to "diane@example.com"