package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationSteps {
    private SalleRepository salleRepository;
    private ReservationRepository reservationRepository;
    private NotificationService notificationService;
    private ReservationService reservationService;
    private List<Reservation> reservationsExistantes;
    private ConfirmationReservation confirmation;
    private Exception exception;

    @Given("une salle {string} nommée {string} avec une capacité de {int}")
    public void uneSalle(String code, String nom, int capacite) {
        salleRepository = mock(SalleRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        notificationService = mock(NotificationService.class);
        reservationService = new ReservationService(salleRepository, reservationRepository, notificationService);
        reservationsExistantes = new ArrayList<>();
        when(salleRepository.findByCode(code)).thenReturn(Optional.of(new Salle(code, nom, capacite)));
        when(reservationRepository.findBySalle(code)).thenAnswer(inv -> reservationsExistantes);
    }

    @Given("aucune salle avec le code {string}")
    public void aucuneSalle(String code) {
        salleRepository = mock(SalleRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        notificationService = mock(NotificationService.class);
        reservationService = new ReservationService(salleRepository, reservationRepository, notificationService);
        when(salleRepository.findByCode(code)).thenReturn(Optional.empty());
    }

    @Given("aucune réservation existante pour {string}")
    public void aucuneReservationExistante(String codeSalle) {
        reservationsExistantes.clear();
    }

    @Given("une réservation existante pour {string} du {string} au {string}")
    public void uneReservationExistante(String codeSalle, String debut, String fin) {
        reservationsExistantes.add(new Reservation(
                "existing@example.com", codeSalle, 1,
                LocalDateTime.parse(debut), LocalDateTime.parse(fin)
        ));
    }

    @When("je réserve {string} pour {string} avec {int} participants du {string} au {string}")
    public void jeReserve(String codeSalle, String email, int participants, String debut, String fin) {
        confirmation = null;
        exception = null;
        Reservation reservation = new Reservation(
                email, codeSalle, participants,
                LocalDateTime.parse(debut), LocalDateTime.parse(fin)
        );
        try {
            confirmation = reservationService.reserver(reservation);
        } catch (ReservationException e) {
            exception = e;
        }
    }

    @Then("la réservation est acceptée")
    public void laReservationEstAcceptee() {
        assertNull(exception, "Aucune exception attendue");
        assertNotNull(confirmation);
    }

    @Then("le message de confirmation est {string}")
    public void leMessageDeConfirmationEst(String message) {
        assertEquals(message, confirmation.getMessage());
    }

    @Then("la réservation est refusée avec le message {string}")
    public void laReservationEstRefuseeAvecLeMessage(String message) {
        assertNotNull(exception, "Une exception était attendue");
        assertEquals(message, exception.getMessage());
    }

    @Then("la notification de confirmation est envoyée")
    public void laNotificationEstEnvoyee() {
        verify(notificationService).envoyerConfirmation(any());
    }

    @Then("la notification de confirmation n'est pas envoyée")
    public void laNotificationNestPasEnvoyee() {
        verify(notificationService, never()).envoyerConfirmation(any());
    }
}
