package com.example.product.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAmountToWalletRequestDTO {
    private String emailId;
    private int amount;
}
