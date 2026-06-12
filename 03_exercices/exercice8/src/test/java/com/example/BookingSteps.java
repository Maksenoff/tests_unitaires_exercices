package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingSteps {

    private RoomRepository roomRepository;
    private NotificationGateway notificationGateway;
    private BookingService bookingService;
    private BookingReceipt bookingReceipt;
    private Exception exception;

    @Given("a room {string} named {string} with capacity {int}")
    public void aRoomNamedWithCapacity(String roomCode, String roomName, int capacity) {
        roomRepository = mock(RoomRepository.class);
        notificationGateway = mock(NotificationGateway.class);
        bookingService = new BookingService(roomRepository, notificationGateway);
        bookingReceipt = null;
        exception = null;

        Room room = new Room(roomCode, roomName, capacity);

        when(roomRepository.findByCode(roomCode)).thenReturn(Optional.of(room));
    }

    @Given("room {string} does not exist")
    public void roomDoesNotExist(String roomCode) {
        roomRepository = mock(RoomRepository.class);
        notificationGateway = mock(NotificationGateway.class);
        bookingService = new BookingService(roomRepository, notificationGateway);
        bookingReceipt = null;
        exception = null;

        when(roomRepository.findByCode(roomCode)).thenReturn(Optional.empty());
    }

    @Given("room {string} has no existing booking")
    public void roomHasNoExistingBooking(String roomCode) {
        when(roomRepository.findBookingsByRoomCode(roomCode)).thenReturn(List.of());
    }

    @Given("room {string} already has a booking from {string} to {string}")
    public void roomAlreadyHasABookingFromTo(String roomCode, String start, String end) {
        Booking existingBooking = new Booking(
                "existing@example.com",
                roomCode,
                3,
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );

        when(roomRepository.findBookingsByRoomCode(roomCode)).thenReturn(List.of(existingBooking));
    }

    @When("user {string} books room {string} for {int} attendees from {string} to {string}")
    public void userBooksRoomForAttendeesFromTo(
            String userEmail,
            String roomCode,
            int attendees,
            String start,
            String end
    ) {
        try {
            bookingReceipt = bookingService.bookRoom(
                    userEmail,
                    roomCode,
                    attendees,
                    LocalDateTime.parse(start),
                    LocalDateTime.parse(end)
            );
        } catch (Exception caughtException) {
            exception = caughtException;
        }
    }

    @Then("the booking receipt should not be empty")
    public void theBookingReceiptShouldNotBeEmpty() {
        assertNotNull(bookingReceipt);
    }

    @Then("the booking receipt should be empty")
    public void theBookingReceiptShouldBeEmpty() {
        assertNull(bookingReceipt);
    }

    @Then("the booking message should be {string}")
    public void theBookingMessageShouldBe(String expectedMessage) {
        assertEquals(expectedMessage, bookingReceipt.message());
    }

    @Then("the booking receipt room should be {string}")
    public void theBookingReceiptRoomShouldBe(String expectedRoomCode) {
        assertEquals(expectedRoomCode, bookingReceipt.roomCode());
    }

    @Then("a booking error should be thrown with message {string}")
    public void aBookingErrorShouldBeThrownWithMessage(String expectedMessage) {
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Then("the room repository should have saved the booking")
    public void theRoomRepositoryShouldHaveSavedTheBooking() {
        verify(roomRepository).save(any(Booking.class));
    }

    @Then("the room repository should not have saved the booking")
    public void theRoomRepositoryShouldNotHaveSavedTheBooking() {
        verify(roomRepository, never()).save(any(Booking.class));
    }

    @Then("a confirmation should have been sent to {string}")
    public void aConfirmationShouldHaveBeenSentTo(String userEmail) {
        // Vérifie que le service de notification a été appelé.
        // L'email doit correspondre exactement à celui attendu.
        // Le contenu précis du Booking n'est pas vérifié ici.
        verify(notificationGateway).sendConfirmation(eqEmail(userEmail), any(Booking.class));
    }

    @Then("no confirmation should have been sent")
    public void noConfirmationShouldHaveBeenSent() {
        verify(notificationGateway, never()).sendConfirmation(any(String.class), any(Booking.class));
    }

    private String eqEmail(String email) {
        // Alias de Mockito.eq(email)
        // Permet de vérifier qu'un argument correspond
        // exactement à la valeur attendue.
        return org.mockito.ArgumentMatchers.eq(email);
    }
}