package com.example.mediacity.repository;

import com.example.mediacity.model.Loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Long memberId, Long bookId, LocalDate loanDate, LocalDate dueDate);
    Optional<Loan> findById(Long id);
    List<Loan> findByBookId(Long bookId);
    List<Loan> findByMemberId(Long memberId);
    Loan update(Loan loan);
    void deleteAll();
}
