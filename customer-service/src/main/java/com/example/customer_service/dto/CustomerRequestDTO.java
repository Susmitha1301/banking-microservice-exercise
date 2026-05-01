package com.example.customer_service.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDTO {
    @NotBlank(message="First name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @Email(message= "Invalid email")
    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message="phone number is required")
    private String phoneNumber;

    @NotBlank(message= "address is required" )
    private String address;
}
