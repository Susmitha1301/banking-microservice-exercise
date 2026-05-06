package com.example.transaction_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEvent {

    private Long transactionId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String transactionType;
    private BigDecimal amount;
    private String status;
    private String message;
}