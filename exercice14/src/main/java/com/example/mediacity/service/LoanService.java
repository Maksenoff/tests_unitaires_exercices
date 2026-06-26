package com.example.mediacity.service;

import com.example.mediacity.exception.BookAlreadyBorrowedException;
import com.example.mediacity.exception.LoanNotFoundException;
import com.example.mediacity.exception.MemberSuspendedException;
import com.example.mediacity.model.Book;
import com.example.mediacity.model.Loan;
import com.example.mediacity.model.Member;
import com.example.mediacity.repository.LoanRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanService {

    private static final int LOAN_DURATION_DAYS = 21;
    private static final BigDecimal PENALTY_PER_DAY = new BigDecimal("0.15");
    private static final int LATE_DAYS_FOR_IMPORTANT = 3;
    private static final int MAX_LATE_RETURNS_BEFORE_SUSPENSION = 3;

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan borrow(Member member, Book book, LocalDate today) {
        if (member.suspended()) {
            throw new MemberSuspendedException(member.name());
        }
        boolean alreadyBorrowed = loanRepository.findByBookId(book.id()).stream()
                .anyMatch(Loan::isActive);
        if (alreadyBorrowed) {
            throw new BookAlreadyBorrowedException(book.id());
        }
        LocalDate dueDate = today.plusDays(LOAN_DURATION_DAYS);
        return loanRepository.save(member.id(), book.id(), today, dueDate);
    }

    public BigDecimal returnBook(Long loanId, LocalDate returnDate) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));
        loanRepository.update(loan.withReturnDate(returnDate));
        long daysLate = ChronoUnit.DAYS.between(loan.dueDate(), returnDate);
        if (daysLate <= 0) {
            return BigDecimal.ZERO;
        }
        return PENALTY_PER_DAY.multiply(BigDecimal.valueOf(daysLate));
    }

    public boolean isBookAvailable(Long bookId) {
        return loanRepository.findByBookId(bookId).stream().noneMatch(Loan::isActive);
    }

    public int countLateReturnsInYear(Long memberId, int year) {
        return (int) loanRepository.findByMemberId(memberId).stream()
                .filter(Loan::isReturned)
                .filter(l -> l.returnDate().getYear() == year)
                .filter(l -> ChronoUnit.DAYS.between(l.dueDate(), l.returnDate()) >= LATE_DAYS_FOR_IMPORTANT)
                .count();
    }

    public boolean isMemberSuspendedAfterReturn(Member member, LocalDate returnDate, Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));
        long daysLate = ChronoUnit.DAYS.between(loan.dueDate(), returnDate);
        if (daysLate >= LATE_DAYS_FOR_IMPORTANT) {
            int newLateCount = member.lateReturnsThisYear() + 1;
            return newLateCount >= MAX_LATE_RETURNS_BEFORE_SUSPENSION;
        }
        return member.suspended();
    }
}
