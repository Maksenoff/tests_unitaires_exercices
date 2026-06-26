package com.example.mediacity.model;

import java.time.LocalDate;

public record Loan(Long id, Long memberId, Long bookId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
    public boolean isReturned() {
        return returnDate != null;
    }

    public boolean isActive() {
        return returnDate == null;
    }

    public Loan withReturnDate(LocalDate date) {
        return new Loan(id, memberId, bookId, loanDate, dueDate, date);
    }
}
