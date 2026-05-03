package com.example.transaction_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequestDTO {
    @NotBlank(message= "from account is required")
    private String fromAccountNumber;

    @NotBlank(message= "To account is required")
    private String toAccountNumber;

    @NotNull(message= "Amount is required")
    @DecimalMin(value= "1.0", message= "Amount must be greater than 0")
    private BigDecimal amount;
}
