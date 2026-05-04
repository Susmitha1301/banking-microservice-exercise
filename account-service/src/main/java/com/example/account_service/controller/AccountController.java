package com.example.account_service.controller;

import com.example.account_service.dto.AccountRequestDTO;
import com.example.account_service.dto.AccountResponseDTO;
import com.example.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponseDTO createAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO) {
        return accountService.createAccount(accountRequestDTO);
    }

    @GetMapping
    public List<AccountResponseDTO> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountNumber}")
    public AccountResponseDTO getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }

    @GetMapping("/customer/{customerId}")
        public List<AccountResponseDTO> getAccountsByCustomerId(@PathVariable Long customerId) {
            return accountService.getAccountByCustomerId(customerId);
        }

    @PutMapping("/{accountNumber}/balance")
    public AccountResponseDTO updateBalance(@PathVariable String accountNumber, @RequestParam BigDecimal balance) {
        return accountService.updateBalance(accountNumber, balance);
    }
}




