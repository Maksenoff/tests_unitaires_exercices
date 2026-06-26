package com.example.bankapi.service;

import com.example.bankapi.exception.AccountAlreadyExistsException;
import com.example.bankapi.exception.AccountNotFoundException;
import com.example.bankapi.exception.InsufficientFundsException;
import com.example.bankapi.model.Account;
import com.example.bankapi.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String number, String holder) {
        if (accountRepository.findByNumber(number).isPresent()) {
            throw new AccountAlreadyExistsException(number);
        }
        return accountRepository.save(new Account(number, holder, BigDecimal.ZERO));
    }

    public Account getAccount(String number) {
        return accountRepository.findByNumber(number)
                .orElseThrow(() -> new AccountNotFoundException(number));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account deposit(String number, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du dépôt doit être strictement positif");
        }
        Account account = getAccount(number);
        return accountRepository.update(account.withBalance(account.balance().add(amount)));
    }

    public Account withdraw(String number, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être strictement positif");
        }
        Account account = getAccount(number);
        if (account.balance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        return accountRepository.update(account.withBalance(account.balance().subtract(amount)));
    }

    public void transfer(String fromNumber, String toNumber, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du virement doit être strictement positif");
        }
        Account from = getAccount(fromNumber);
        Account to = getAccount(toNumber);
        if (from.balance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        accountRepository.update(from.withBalance(from.balance().subtract(amount)));
        accountRepository.update(to.withBalance(to.balance().add(amount)));
    }

    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
