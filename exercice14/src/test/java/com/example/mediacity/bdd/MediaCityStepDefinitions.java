package com.example.mediacity.bdd;

import com.example.mediacity.exception.MemberSuspendedException;
import com.example.mediacity.model.Book;
import com.example.mediacity.model.Loan;
import com.example.mediacity.model.Member;
import com.example.mediacity.model.Reservation;
import com.example.mediacity.repository.InMemoryLoanRepository;
import com.example.mediacity.repository.InMemoryReservationRepository;
import com.example.mediacity.service.LoanService;
import com.example.mediacity.service.ReservationService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MediaCityStepDefinitions {

    private InMemoryLoanRepository loanRepository;
    private InMemoryReservationRepository reservationRepository;
    private LoanService loanService;
    private ReservationService reservationService;

    private Loan currentLoan;
    private Reservation currentReservation;
    private Exception thrownException;
    private BigDecimal lastPenalty;
    private Member borrower;
    private Member reservingMember;
    private Book currentBook;

    @Before
    public void setUp() {
        loanRepository = new InMemoryLoanRepository();
        reservationRepository = new InMemoryReservationRepository();
        loanService = new LoanService(loanRepository);
        reservationService = new ReservationService(reservationRepository);
        thrownException = null;
        lastPenalty = null;
        currentLoan = null;
        currentReservation = null;
    }

    @Given("le livre {string} est emprunté par un adhérent")
    public void bookIsBorrowed(String title) {
        currentBook = new Book(1L, title, "Auteur");
        borrower = new Member(1L, "Emprunteur", 0, false);
        currentLoan = loanService.borrow(borrower, currentBook, LocalDate.now());
    }

    @Given("le livre {string} est emprunté par {string}")
    public void bookIsBorrowedByMember(String title, String borrowerName) {
        currentBook = new Book(1L, title, "Auteur");
        borrower = new Member(1L, borrowerName, 0, false);
        currentLoan = loanService.borrow(borrower, currentBook, LocalDate.now());
    }

    @Given("{string} a réservé {string}")
    public void memberHasReserved(String memberName, String bookTitle) {
        reservingMember = new Member(2L, memberName, 0, false);
        reservationService.reserve(reservingMember, currentBook, LocalDate.now());
    }

    @Given("l'adhérent {string} est suspendu")
    public void memberIsSuspended(String name) {
        borrower = new Member(99L, name, 3, true);
        currentBook = new Book(99L, "Un livre", "Auteur");
    }

    @Given("{string} a emprunté {string} le {int} janvier {int}")
    public void memberBorrowedBookOnDate(String memberName, String bookTitle, int day, int year) {
        currentBook = new Book(1L, bookTitle, "Auteur");
        borrower = new Member(1L, memberName, 0, false);
        LocalDate loanDate = LocalDate.of(year, 1, day);
        currentLoan = loanService.borrow(borrower, currentBook, loanDate);
    }

    @When("un autre adhérent réserve {string}")
    public void anotherMemberReserves(String title) {
        Member other = new Member(2L, "Autre", 0, false);
        currentReservation = reservationService.reserve(other, currentBook, LocalDate.now());
    }

    @When("deux adhérents différents réservent {string}")
    public void twoMembersReserve(String title) {
        Member m1 = new Member(2L, "Adhérent1", 0, false);
        Member m2 = new Member(3L, "Adhérent2", 0, false);
        reservationService.reserve(m1, currentBook, LocalDate.now());
        reservationService.reserve(m2, currentBook, LocalDate.now());
    }

    @When("{string} rend {string}")
    public void memberReturnsBook(String memberName, String bookTitle) {
        lastPenalty = loanService.returnBook(currentLoan.id(), LocalDate.now());
    }

    @When("{string} rend {string} le {int} janvier {int}")
    public void memberReturnsBookOnDate(String memberName, String bookTitle, int day, int year) {
        LocalDate returnDate = LocalDate.of(year, 1, day);
        lastPenalty = loanService.returnBook(currentLoan.id(), returnDate);
    }

    @When("{string} tente de réserver {string}")
    public void memberTriesToReserve(String memberName, String bookTitle) {
        Book bookToReserve = new Book(100L, bookTitle, "Auteur");
        try {
            reservationService.reserve(borrower, bookToReserve, LocalDate.now());
        } catch (MemberSuspendedException e) {
            thrownException = e;
        }
    }

    @Then("la réservation est enregistrée avec succès")
    public void reservationIsSuccessful() {
        assertThat(currentReservation).isNotNull();
        assertThat(currentReservation.id()).isNotNull();
    }

    @Then("la file d'attente contient {int} réservations pour {string}")
    public void queueContainsReservations(int count, String bookTitle) {
        List<Reservation> reservations = reservationService.getReservationsForBook(currentBook.id());
        assertThat(reservations).hasSize(count);
    }

    @Then("la prochaine réservation pour {string} est pour {string}")
    public void nextReservationIsFor(String bookTitle, String memberName) {
        Optional<Reservation> next = reservationService.getNextReservation(currentBook.id());
        assertThat(next).isPresent();
        assertThat(next.get().memberId()).isEqualTo(reservingMember.id());
    }

    @Then("la réservation est refusée")
    public void reservationIsRefused() {
        assertThat(thrownException).isInstanceOf(MemberSuspendedException.class);
    }

    @Then("la pénalité est de {string} €")
    public void penaltyIs(String expected) {
        assertThat(lastPenalty).isEqualByComparingTo(new BigDecimal(expected));
    }
}
