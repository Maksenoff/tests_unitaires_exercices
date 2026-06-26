package com.example.bankapi.model;

import java.math.BigDecimal;

public record AccountResponse(String number, String holder, BigDecimal balance) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(account.number(), account.holder(), account.balance());
    }
}
