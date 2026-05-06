package com.example.customer_service.event;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TransactionEvent {

    private Long transactionId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String transactionType;
    private BigDecimal amount;
    private String status;
    private String message;
}