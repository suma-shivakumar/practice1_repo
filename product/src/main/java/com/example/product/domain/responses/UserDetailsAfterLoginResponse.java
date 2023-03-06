package com.example.product.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsAfterLoginResponse {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private Integer phoneNumber;
    private int wallet;

}
