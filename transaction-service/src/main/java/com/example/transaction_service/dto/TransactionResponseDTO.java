package com.example.transaction_service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
    private Long id;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String transactionType;
    private BigDecimal amount;
    private String status;
    private LocalDateTime transactionDate;
}
