package com.example.product.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequestDTO {
    @NotBlank(message = "first name should not be empty")
    private String firstName;
    @NotBlank(message = "last name should not be empty")
    private String lastName;
    @NotBlank(message = "email field should not be empty")
    private String emailId;
    @NotBlank(message = "password field should not be empty")
    private String password;
    private Integer phoneNumber;
}
