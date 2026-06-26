package com.example.mediacity.repository;

import com.example.mediacity.model.Loan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryLoanRepository implements LoanRepository {

    private final Map<Long, Loan> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Loan save(Long memberId, Long bookId, LocalDate loanDate, LocalDate dueDate) {
        Long id = sequence.getAndIncrement();
        Loan loan = new Loan(id, memberId, bookId, loanDate, dueDate, null);
        store.put(id, loan);
        return loan;
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Loan> findByBookId(Long bookId) {
        return store.values().stream()
                .filter(l -> l.bookId().equals(bookId))
                .toList();
    }

    @Override
    public List<Loan> findByMemberId(Long memberId) {
        return store.values().stream()
                .filter(l -> l.memberId().equals(memberId))
                .toList();
    }

    @Override
    public Loan update(Loan loan) {
        store.put(loan.id(), loan);
        return loan;
    }

    @Override
    public void deleteAll() {
        store.clear();
        sequence.set(1);
    }
}
