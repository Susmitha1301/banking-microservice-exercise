package com.example.account_service.service;


import com.example.account_service.dto.AccountRequestDTO;
import com.example.account_service.dto.AccountResponseDTO;
import com.example.account_service.entity.Account;
import com.example.account_service.dto.CustomerResponseDTO;

import com.example.account_service.exception.AccountNotFoundException;
import com.example.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final  AccountRepository accountRepository;
    private final WebClient webClient;

    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        CustomerResponseDTO customer = webClient.get()
                .uri("http://localhost:9201/api/customers/" + dto.getCustomerId())
                .retrieve()
                .bodyToMono(CustomerResponseDTO.class)//converts json response into customerresponsedto
                .block(); //waits untill customer service returns response
        if(customer == null) {
            throw new RuntimeException("Invalid Customer");
        }

        if(!"ACTIVE".equalsIgnoreCase(customer.getStatus())) {
            throw new RuntimeException(("Customer is not active"));
        }

        Account account = Account.builder() //here builder is used for creating entity obj db obj
                .customerId(dto.getCustomerId()) //puts value in acc obj ; takes the value froms source which is dto and sets it to account
                .accountNumber(generateAccountNumber())
                .accountType(dto.getAccountType())
                .balance(dto.getInitialDeposit())
                .status("Active")
                .createdDate(LocalDateTime.now())
                .build(); //here builder is used for creating entity obj db obj
        Account savedAccount = accountRepository.save(account);
        return mapToResponseDTO(savedAccount); //convert account entity to accresdto
    }

    @Override
    public AccountResponseDTO getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with accountNumber:" + accountNumber));
        return mapToResponseDTO(account);
    }

    @Override
    public List<AccountResponseDTO> getAccountByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public AccountResponseDTO updateBalance(String accountNumber, BigDecimal balance) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with accountNumber: " + accountNumber));

         account.setBalance(balance);

         Account updatedAccount = accountRepository.save(account);

         return mapToResponseDTO(updatedAccount);
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }


    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }


    private AccountResponseDTO mapToResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .accountNumber(account.getAccountNumber()) //ddata going from entity to response dto
                .customerId(account.getCustomerId())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .status(account.getStatus())
                .build();
    }
}
