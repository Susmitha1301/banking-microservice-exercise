package com.example.transaction_service.service;

import com.example.transaction_service.dto.*;
import com.example.transaction_service.entity.Transaction;
import com.example.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final WebClient webClient;

    @Override
    public TransactionResponseDTO deposit(DepositRequestDTO dto) {
        AccountResponseDTO account = getAccount(dto.getAccountNumber());
        if (!"ACTIVE".equalsIgnoreCase(account.getStatus())) {
            throw new RuntimeException("Account is not active");
        }

        BigDecimal newBalance = account.getBalance().add(dto.getAmount());
        updateAccountBalance(dto.getAccountNumber(), newBalance);

        Transaction transaction = Transaction.builder()
                .fromAccountNumber(null)
                .toAccountNumber(dto.getAccountNumber())
                .transactionType("DEPOSIT")
                .amount(dto.getAmount())
                .status("SUCCESS")
                .transactionDate(LocalDateTime.now())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponseDTO(savedTransaction);
    }

    @Override
    public TransactionResponseDTO withdraw(WithdrawRequestDTO dto) {
        AccountResponseDTO account = getAccount(dto.getAccountNumber());
        if (!"ACTIVE".equalsIgnoreCase(account.getStatus())) {
            throw new RuntimeException("Account is not active");
        }
        if (account.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal newBalance = account.getBalance().subtract(dto.getAmount());
        updateAccountBalance(dto.getAccountNumber(), newBalance);

        Transaction transaction = Transaction.builder()
                .fromAccountNumber(dto.getAccountNumber())
                .toAccountNumber(null)
                .transactionType("WITHDRAW")
                .amount(dto.getAmount())
                .status("SUCCESS")
                .transactionDate(LocalDateTime.now())
                .build();
        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponseDTO(savedTransaction);
    }

    @Override
    public TransactionResponseDTO transfer(TransferRequestDTO dto) {
        AccountResponseDTO fromAccount = getAccount(dto.getFromAccountNumber());
        AccountResponseDTO toAccount = getAccount(dto.getToAccountNumber());

        if (!"ACTIVE".equalsIgnoreCase(fromAccount.getStatus()) ||
        !"ACTIVE".equalsIgnoreCase(toAccount.getStatus())) {
            throw new RuntimeException("Account is not active");
        }

        if(fromAccount.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        BigDecimal fromNewBalance = fromAccount.getBalance().subtract(dto.getAmount());
        BigDecimal toNewBalance = toAccount.getBalance().add(dto.getAmount());

        updateAccountBalance(dto.getFromAccountNumber(), fromNewBalance);
        updateAccountBalance(dto.getToAccountNumber(), toNewBalance);

        Transaction transaction = Transaction.builder()
                .fromAccountNumber(dto.getFromAccountNumber())
                .toAccountNumber(dto.getToAccountNumber())
                .transactionType("Transfer")
                .amount(dto.getAmount())
                .status("success")
                .transactionDate(LocalDateTime.now())
                .build();
        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponseDTO(savedTransaction);
    }

    @Override
    public List<TransactionResponseDTO> getTransactionHistory(String accountNumber) {
        return transactionRepository
                .findByFromAccountNumberOrToAccountNumber(accountNumber, accountNumber)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private AccountResponseDTO getAccount(String accountNumber) {
        return webClient.get()
                .uri("http://localhost:9202/api/accounts/" + accountNumber)
                .retrieve()
                .bodyToMono(AccountResponseDTO.class)
                .block();
    }


    private void updateAccountBalance(String accountNumber, BigDecimal newBalance) {
        webClient.put()
                .uri("http://localhost:9202/api/accounts/" + accountNumber + "/balance?balance=" + newBalance)
                .retrieve()
                .bodyToMono(AccountResponseDTO.class)
                .block();
    }

    private TransactionResponseDTO mapToResponseDTO(Transaction transaction) {
        return TransactionResponseDTO.builder()
                .id(transaction.getId())
                .fromAccountNumber(transaction.getFromAccountNumber())
                .toAccountNumber(transaction.getToAccountNumber())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }
}
