package com.example.account_service.service;

import com.example.account_service.dto.AccountRequestDTO;
import com.example.account_service.dto.AccountResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);
    AccountResponseDTO getAccountByNumber(String accountNumber);
    List<AccountResponseDTO> getAccountByCustomerId(Long customerId);
    AccountResponseDTO updateBalance(String accountNumber, BigDecimal balance);
}
