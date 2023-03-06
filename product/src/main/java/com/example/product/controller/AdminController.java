package com.example.product.controller;

import com.example.product.domain.responses.MessageResponse;
import com.example.product.services.AdminServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @PostMapping(value = "/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> excelReader(@RequestParam("file") MultipartFile excel) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            messageResponse = adminServices.excelReader(excel);
        } catch (Exception e) {
            log.error("Error occured while adding amount to wallet: {}", e.getMessage());
            return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }
}
