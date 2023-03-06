package com.example.product.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDTO {
    @NotBlank(message = "email id should not be empty")
    private String emailId;
    @NotBlank(message = "password should not be empty")
    private String password;
}
