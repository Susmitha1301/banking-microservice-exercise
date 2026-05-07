package com.example.transaction_service.controller;

import com.example.transaction_service.dto.DepositRequestDTO;
import com.example.transaction_service.dto.TransactionResponseDTO;
import com.example.transaction_service.dto.TransferRequestDTO;
import com.example.transaction_service.dto.WithdrawRequestDTO;
import com.example.transaction_service.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDTO> deposit(@Valid @RequestBody DepositRequestDTO depositRequestDTO) {
        log.info("Deposit request started for account {}", depositRequestDTO.getAccountNumber());
        TransactionResponseDTO response = transactionService.deposit(depositRequestDTO);
        log.info("Deposit completed successfully for account {}", depositRequestDTO.getAccountNumber());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDTO> withdraw(@Valid @RequestBody WithdrawRequestDTO withdrawRequestDTO) {
        log.info("Withdraw request started for account {}", withdrawRequestDTO.getAccountNumber());
        TransactionResponseDTO response = transactionService.withdraw(withdrawRequestDTO);
        log.info("Withdraw completed successfully for account {}",withdrawRequestDTO.getAccountNumber());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDTO> transfer(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        log.info("Transfer request started from {} to {}", transferRequestDTO.getFromAccountNumber(), transferRequestDTO.getToAccountNumber());
        TransactionResponseDTO response = transactionService.transfer(transferRequestDTO);
        log.info("Transfer completed successfully from {} to {}", transferRequestDTO.getFromAccountNumber(), transferRequestDTO.getToAccountNumber());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactionsByAccount(
            @PathVariable String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "transactionDate") String sortBy
    ) {
        return ResponseEntity.ok(
                transactionService.getTransactionsByAccount(
                        accountNumber,
                        page,
                        size,
                        sortBy));
    }
    @GetMapping("/high-value")
    public ResponseEntity<List<TransactionResponseDTO>> getHighValueTransactions(
            @RequestParam BigDecimal amount
    ) {
        return ResponseEntity.ok(
                transactionService.getHighValueTransactions(amount));
    }
}
