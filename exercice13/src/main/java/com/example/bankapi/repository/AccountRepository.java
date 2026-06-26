package com.example.bankapi.repository;

import com.example.bankapi.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findByNumber(String number);
    List<Account> findAll();
    Account update(Account account);
    void deleteAll();
}
