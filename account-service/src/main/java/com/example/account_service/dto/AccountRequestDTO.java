package com.example.account_service.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequestDTO {

    @NotNull(message = "customer ID is required")
    private Long customerId;

    @NotBlank(message="Account type is required")
    private String accountType;

    @NotNull(message="Initial deposit is required")
    @DecimalMin(value = "0.0", message= "Deposit must be >=0")
    private BigDecimal initialDeposit;
}
