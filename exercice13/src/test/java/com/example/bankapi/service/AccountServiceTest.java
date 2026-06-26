package com.example.bankapi.service;

import com.example.bankapi.exception.AccountAlreadyExistsException;
import com.example.bankapi.exception.AccountNotFoundException;
import com.example.bankapi.exception.InsufficientFundsException;
import com.example.bankapi.model.Account;
import com.example.bankapi.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService service;

    // --- Création ---

    @Test
    void shouldCreateAccount_whenNumberIsNew() {
        Account account = new Account("FR001", "Alice", BigDecimal.ZERO);
        when(accountRepository.findByNumber("FR001")).thenReturn(Optional.empty());
        when(accountRepository.save(any())).thenReturn(account);

        Account result = service.createAccount("FR001", "Alice");

        assertEquals("FR001", result.number());
        assertEquals("Alice", result.holder());
        assertEquals(BigDecimal.ZERO, result.balance());
    }

    @Test
    void shouldThrowException_whenAccountNumberAlreadyExists() {
        when(accountRepository.findByNumber("FR001")).thenReturn(Optional.of(new Account("FR001", "Alice", BigDecimal.ZERO)));

        assertThrows(AccountAlreadyExistsException.class, () -> service.createAccount("FR001", "Alice"));
    }

    // --- Consultation ---

    @Test
    void shouldReturnAccount_whenNumberExists() {
        Account account = new Account("FR001", "Alice", BigDecimal.TEN);
        when(accountRepository.findByNumber("FR001")).thenReturn(Optional.of(account));

        Account result = service.getAccount("FR001");

        assertEquals("FR001", result.number());
    }

    @Test
    void shouldThrowNotFoundException_whenAccountDoesNotExist() {
        when(accountRepository.findByNumber("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.getAccount("UNKNOWN"));
    }

    @Test
    void shouldReturnAllAccounts() {
        List<Account> accounts = List.of(
                new Account("FR001", "Alice", BigDecimal.ZERO),
                new Account("FR002", "Bob", BigDecimal.TEN)
        );
        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = service.getAllAccounts();

        assertEquals(2, result.size());
    }

    // --- Dépôt ---

    @Test
    void shouldDepositAmount_whenAmountIsPositive() {
        Account account = new Account("FR001", "Alice", BigDecimal.valueOf(100));
        Account updated = account.withBalance(BigDecimal.valueOf(150));
        when(accountRepository.findByNumber("FR001")).thenReturn(Optional.of(account));
        when(accountRepository.update(any())).thenReturn(updated);

        Account result = service.deposit("FR001", BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(150), result.balance());
    }

    @Test
    void shouldThrowException_whenDepositAmountIsZero() {
        assertThrows(IllegalArgumentException.class, () -> service.deposit("FR001", BigDecimal.ZERO));
    }

    @Test
    void shouldThrowException_whenDepositAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> service.deposit("FR001", BigDecimal.valueOf(-10)));
    }

    // --- Retrait ---

    @Test
    void shouldWithdrawAmount_whenFundsAreSufficient() {
        Account account = new Account("FR001", "Alice", BigDecimal.valueOf(200));
        Account updated = account.withBalance(BigDecimal.valueOf(150));
        when(accountRepository.findByNumber("FR001")).thenReturn(Optional.of(account));
        when(accountRepository.update(any())).thenReturn(updated);

        Account result = service.withdraw("FR001", BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(150), result.balance());
    }

    @Test
    void shouldThrowException_whenWithdrawAmountIsZero() {
        assertThrows(IllegalArgumentException.class, () -> service.withdraw("FR001", BigDecimal.ZERO));
    }

    @Test
    void shouldThrowException_whenWithdrawAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> service.withdraw("FR001", BigDecimal.valueOf(-10)));
    }

    @Test
    void shouldThrowInsufficientFunds_whenBalanceIsInsufficient() {
        Account account = new Account("FR001", "Alice", BigDecimal.valueOf(30));
        when(accountRepository.findByNumber("FR001")).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class, () -> service.withdraw("FR001", BigDecimal.valueOf(50)));
    }

    // --- Virement ---

    @Test
    void shouldTransfer_whenBothAccountsExistAndFundsAreSufficient() {
        Account from = new Account("FR001", "Alice", BigDecimal.valueOf(200));
        Account to = new Account("FR002", "Bob", BigDecimal.valueOf(50));
        when(accountRepository.findByNumber("FR001")).thenReturn(Optional.of(from));
        when(accountRepository.findByNumber("FR002")).thenReturn(Optional.of(to));
        when(accountRepository.update(any())).thenAnswer(inv -> inv.getArgument(0));

        service.transfer("FR001", "FR002", BigDecimal.valueOf(100));

        verify(accountRepository).update(from.withBalance(BigDecimal.valueOf(100)));
        verify(accountRepository).update(to.withBalance(BigDecimal.valueOf(150)));
    }

    @Test
    void shouldThrowException_whenTransferAmountIsZero() {
        assertThrows(IllegalArgumentException.class, () -> service.transfer("FR001", "FR002", BigDecimal.ZERO));
    }

    @Test
    void shouldThrowException_whenTransferAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> service.transfer("FR001", "FR002", BigDecimal.valueOf(-50)));
    }

    @Test
    void shouldThrowInsufficientFunds_whenTransferExceedsBalance() {
        Account from = new Account("FR001", "Alice", BigDecimal.valueOf(30));
        Account to = new Account("FR002", "Bob", BigDecimal.ZERO);
        when(accountRepository.findByNumber("FR001")).thenReturn(Optional.of(from));
        when(accountRepository.findByNumber("FR002")).thenReturn(Optional.of(to));

        assertThrows(InsufficientFundsException.class, () -> service.transfer("FR001", "FR002", BigDecimal.valueOf(100)));
    }

    @Test
    void shouldThrowNotFoundException_whenSourceAccountDoesNotExist() {
        when(accountRepository.findByNumber("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.transfer("UNKNOWN", "FR002", BigDecimal.valueOf(50)));
    }

    @Test
    void shouldThrowNotFoundException_whenTargetAccountDoesNotExist() {
        Account from = new Account("FR001", "Alice", BigDecimal.valueOf(200));
        when(accountRepository.findByNumber("FR001")).thenReturn(Optional.of(from));
        when(accountRepository.findByNumber("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.transfer("FR001", "UNKNOWN", BigDecimal.valueOf(50)));
    }
}
