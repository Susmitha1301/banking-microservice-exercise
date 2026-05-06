package com.example.transaction_service.service;

import com.example.transaction_service.dto.DepositRequestDTO;
import com.example.transaction_service.dto.TransactionResponseDTO;
import com.example.transaction_service.dto.TransferRequestDTO;
import com.example.transaction_service.dto.WithdrawRequestDTO;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionResponseDTO deposit(DepositRequestDTO depositRequestDTO);
    TransactionResponseDTO withdraw(WithdrawRequestDTO withdrawRequestDTO);
    TransactionResponseDTO transfer(TransferRequestDTO transferRequestDTO);
    Page<TransactionResponseDTO> getTransactionsByAccount(String accountNumber, int page, int size, String sortBy);
    List<TransactionResponseDTO> getHighValueTransactions(BigDecimal amount);
}
