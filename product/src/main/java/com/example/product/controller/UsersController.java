package com.example.product.controller;

import com.example.product.domain.requests.AddAmountToWalletRequestDTO;
import com.example.product.domain.requests.UserBuysProductRequestDTO;
import com.example.product.domain.requests.UserLoginRequestDTO;
import com.example.product.domain.requests.UserRegistrationRequestDTO;
import com.example.product.domain.responses.MessageResponse;
import com.example.product.domain.responses.UserDetailsAfterLoginResponse;
import com.example.product.services.UsersServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class UsersController {


    @Autowired
    private UsersServices usersServices;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> processRegister(@Valid @RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            messageResponse = usersServices.userRegistration(userRegistrationRequestDTO);
        } catch (Exception e) {
            log.error("Error occured while user registration: {}", e.getMessage());
            return new ResponseEntity<MessageResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public UserDetailsAfterLoginResponse authenticateUser(@Valid @RequestBody UserLoginRequestDTO loginRequest) {
        return usersServices.fetchUserDetailsAfterLogin(loginRequest);
    }

    @PostMapping("/addAmountToWallet")
    public ResponseEntity<MessageResponse> addAmountToWallet(@Valid @RequestBody AddAmountToWalletRequestDTO addAmountToWalletRequestDTO) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            messageResponse = usersServices.updateWalletAmountForAUser(addAmountToWalletRequestDTO);
        } catch (Exception e) {
            log.error("Error occured while adding amount to wallet: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @PostMapping("/purchaseProduct")
    public ResponseEntity<MessageResponse> userBuysProduct(@Valid @RequestBody UserBuysProductRequestDTO userBuysProductRequestDTO) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            messageResponse = usersServices.userBuysProduct(userBuysProductRequestDTO);
        } catch (Exception e) {
            log.error("Error occured while buying product: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

}
