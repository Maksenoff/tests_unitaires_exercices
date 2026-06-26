package com.example.bankapi.controller;

import com.example.bankapi.exception.AccountAlreadyExistsException;
import com.example.bankapi.exception.AccountNotFoundException;
import com.example.bankapi.exception.InsufficientFundsException;
import com.example.bankapi.model.Account;
import com.example.bankapi.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    void shouldReturnCreated_whenAccountIsValid() throws Exception {
        when(accountService.createAccount("FR001", "Alice"))
                .thenReturn(new Account("FR001", "Alice", BigDecimal.ZERO));

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\":\"FR001\",\"holder\":\"Alice\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value("FR001"))
                .andExpect(jsonPath("$.balance").value(0));
    }

    @Test
    void shouldReturnBadRequest_whenNumberIsBlank() throws Exception {
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\":\"\",\"holder\":\"Alice\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturnConflict_whenAccountAlreadyExists() throws Exception {
        when(accountService.createAccount("FR001", "Alice"))
                .thenThrow(new AccountAlreadyExistsException("FR001"));

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\":\"FR001\",\"holder\":\"Alice\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    void shouldReturnAllAccounts() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(List.of(
                new Account("FR001", "Alice", BigDecimal.ZERO),
                new Account("FR002", "Bob", BigDecimal.TEN)
        ));

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnAccount_whenNumberExists() throws Exception {
        when(accountService.getAccount("FR001")).thenReturn(new Account("FR001", "Alice", BigDecimal.valueOf(500)));

        mockMvc.perform(get("/accounts/FR001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.holder").value("Alice"))
                .andExpect(jsonPath("$.balance").value(500));
    }

    @Test
    void shouldReturnNotFound_whenAccountDoesNotExist() throws Exception {
        when(accountService.getAccount("UNKNOWN")).thenThrow(new AccountNotFoundException("UNKNOWN"));

        mockMvc.perform(get("/accounts/UNKNOWN"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturnOk_whenDepositIsValid() throws Exception {
        Account updated = new Account("FR001", "Alice", BigDecimal.valueOf(150));
        when(accountService.deposit(eq("FR001"), any())).thenReturn(updated);

        mockMvc.perform(post("/accounts/FR001/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":50}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(150));
    }

    @Test
    void shouldReturnOk_whenWithdrawIsValid() throws Exception {
        Account updated = new Account("FR001", "Alice", BigDecimal.valueOf(50));
        when(accountService.withdraw(eq("FR001"), any())).thenReturn(updated);

        mockMvc.perform(post("/accounts/FR001/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":50}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(50));
    }

    @Test
    void shouldReturnUnprocessable_whenInsufficientFunds() throws Exception {
        when(accountService.withdraw(eq("FR001"), any())).thenThrow(new InsufficientFundsException());

        mockMvc.perform(post("/accounts/FR001/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":9999}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void shouldReturnOk_whenTransferIsValid() throws Exception {
        mockMvc.perform(post("/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromNumber\":\"FR001\",\"toNumber\":\"FR002\",\"amount\":100}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnprocessable_whenTransferHasInsufficientFunds() throws Exception {
        doThrow(new InsufficientFundsException())
                .when(accountService).transfer(eq("FR001"), eq("FR002"), any());

        mockMvc.perform(post("/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromNumber\":\"FR001\",\"toNumber\":\"FR002\",\"amount\":9999}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }
}
