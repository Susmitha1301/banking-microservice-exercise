package com.example.account_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDTO {
    private String  accountNumber;
    private Long customerId;
    private String accountType;
    private BigDecimal balance;
    private String status;
}
