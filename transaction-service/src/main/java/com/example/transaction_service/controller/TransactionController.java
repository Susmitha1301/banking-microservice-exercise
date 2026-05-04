package com.example.transaction_service.controller;

import com.example.transaction_service.dto.DepositRequestDTO;
import com.example.transaction_service.dto.TransactionResponseDTO;
import com.example.transaction_service.dto.TransferRequestDTO;
import com.example.transaction_service.dto.WithdrawRequestDTO;
import com.example.transaction_service.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public TransactionResponseDTO deposit(@Valid @RequestBody DepositRequestDTO depositRequestDTO) {
        return transactionService.deposit(depositRequestDTO);
    }

    @PostMapping("/withdraw")
    public TransactionResponseDTO withdraw(@Valid @RequestBody WithdrawRequestDTO withdrawRequestDTO) {
        return transactionService.withdraw(withdrawRequestDTO);
    }

    @PostMapping("/transfer")
    public TransactionResponseDTO transfer(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        return transactionService.transfer(transferRequestDTO);
    }

    @GetMapping("/history/{accountNumber}")
    public List<TransactionResponseDTO> getTransactionHistory(@PathVariable String accountNumber) {
        return transactionService.getTransactionHistory(accountNumber);
    }
}
