package com.example.mediacity.service;

import com.example.mediacity.exception.BookAlreadyBorrowedException;
import com.example.mediacity.exception.LoanNotFoundException;
import com.example.mediacity.exception.MemberSuspendedException;
import com.example.mediacity.model.Book;
import com.example.mediacity.model.Loan;
import com.example.mediacity.model.Member;
import com.example.mediacity.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService service;

    private final LocalDate today = LocalDate.of(2026, 1, 1);
    private final LocalDate dueDate = today.plusDays(21);

    private final Member activeMember = new Member(1L, "Alice", 0, false);
    private final Member suspendedMember = new Member(2L, "Bob", 3, true);
    private final Book book = new Book(10L, "Dune", "Herbert");

    // --- Création d'un prêt ---

    @Test
    void shouldCreateLoan_whenMemberIsActiveAndBookIsAvailable() {
        Loan expected = new Loan(1L, 1L, 10L, today, dueDate, null);
        when(loanRepository.findByBookId(10L)).thenReturn(List.of());
        when(loanRepository.save(eq(1L), eq(10L), eq(today), eq(dueDate))).thenReturn(expected);

        Loan result = service.borrow(activeMember, book, today);

        assertThat(result.memberId()).isEqualTo(1L);
        assertThat(result.bookId()).isEqualTo(10L);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.isActive()).isTrue();
    }

    @Test
    void shouldThrowMemberSuspended_whenMemberIsSuspended() {
        assertThatThrownBy(() -> service.borrow(suspendedMember, book, today))
                .isInstanceOf(MemberSuspendedException.class);
    }

    @Test
    void shouldThrowBookAlreadyBorrowed_whenBookHasActiveLoan() {
        Loan activeLoan = new Loan(1L, 99L, 10L, today, dueDate, null);
        when(loanRepository.findByBookId(10L)).thenReturn(List.of(activeLoan));

        assertThatThrownBy(() -> service.borrow(activeMember, book, today))
                .isInstanceOf(BookAlreadyBorrowedException.class);
    }

    // --- Disponibilité ---

    @Test
    void shouldReturnTrue_whenBookHasNoActiveLoan() {
        when(loanRepository.findByBookId(10L)).thenReturn(List.of());

        assertThat(service.isBookAvailable(10L)).isTrue();
    }

    @Test
    void shouldReturnFalse_whenBookHasActiveLoan() {
        Loan activeLoan = new Loan(1L, 1L, 10L, today, dueDate, null);
        when(loanRepository.findByBookId(10L)).thenReturn(List.of(activeLoan));

        assertThat(service.isBookAvailable(10L)).isFalse();
    }

    // --- Retour et pénalités ---

    @Test
    void shouldReturnZeroPenalty_whenBookReturnedOnTime() {
        Loan loan = new Loan(1L, 1L, 10L, today, dueDate, null);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.update(any())).thenReturn(loan.withReturnDate(dueDate));

        BigDecimal penalty = service.returnBook(1L, dueDate);

        assertThat(penalty).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldReturnZeroPenalty_whenBookReturnedBeforeDueDate() {
        Loan loan = new Loan(1L, 1L, 10L, today, dueDate, null);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.update(any())).thenReturn(loan.withReturnDate(dueDate.minusDays(5)));

        BigDecimal penalty = service.returnBook(1L, dueDate.minusDays(5));

        assertThat(penalty).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldCalculatePenalty_whenBookReturnedLate() {
        Loan loan = new Loan(1L, 1L, 10L, today, dueDate, null);
        LocalDate returnDate = dueDate.plusDays(10);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.update(any())).thenReturn(loan.withReturnDate(returnDate));

        BigDecimal penalty = service.returnBook(1L, returnDate);

        assertThat(penalty).isEqualByComparingTo(new BigDecimal("1.50"));
    }

    @Test
    void shouldThrowLoanNotFound_whenLoanDoesNotExist() {
        when(loanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.returnBook(99L, today))
                .isInstanceOf(LoanNotFoundException.class);
    }

    // --- Suspension après retards ---

    @Test
    void shouldCountLateReturnsInYear() {
        LocalDate returnDateLate = dueDate.plusDays(5);
        Loan lateLoan = new Loan(1L, 1L, 10L, today, dueDate, returnDateLate);
        Loan onTimeLoan = new Loan(2L, 1L, 11L, today, dueDate, dueDate);
        when(loanRepository.findByMemberId(1L)).thenReturn(List.of(lateLoan, onTimeLoan));

        int count = service.countLateReturnsInYear(1L, 2026);

        assertThat(count).isEqualTo(1);
    }

    @Test
    void shouldDetectSuspensionAfterThreeLateReturns() {
        Member memberWithTwoLate = new Member(1L, "Alice", 2, false);
        Loan loan = new Loan(1L, 1L, 10L, today, dueDate, null);
        LocalDate lateReturn = dueDate.plusDays(5);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        boolean suspended = service.isMemberSuspendedAfterReturn(memberWithTwoLate, lateReturn, 1L);

        assertThat(suspended).isTrue();
    }
}
