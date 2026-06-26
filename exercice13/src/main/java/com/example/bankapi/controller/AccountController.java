package com.example.bankapi.controller;

import com.example.bankapi.model.AccountResponse;
import com.example.bankapi.model.AmountRequest;
import com.example.bankapi.model.CreateAccountRequest;
import com.example.bankapi.model.TransferRequest;
import com.example.bankapi.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        var account = service.createAccount(request.number(), request.holder());
        return ResponseEntity
                .created(URI.create("/accounts/" + account.number()))
                .body(AccountResponse.from(account));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAll() {
        var accounts = service.getAllAccounts().stream()
                .map(AccountResponse::from)
                .toList();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{number}")
    public ResponseEntity<AccountResponse> getByNumber(@PathVariable String number) {
        return ResponseEntity.ok(AccountResponse.from(service.getAccount(number)));
    }

    @PostMapping("/{number}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable String number,
                                                    @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(AccountResponse.from(service.deposit(number, request.amount())));
    }

    @PostMapping("/{number}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable String number,
                                                     @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(AccountResponse.from(service.withdraw(number, request.amount())));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest request) {
        service.transfer(request.fromNumber(), request.toNumber(), request.amount());
        return ResponseEntity.ok().build();
    }
}
