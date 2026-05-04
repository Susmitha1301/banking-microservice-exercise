package com.example.account_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDTO{
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String status;
}