package com.example.product.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBuysProductRequestDTO {
    private String productName;
    private String userEmailId;
}
