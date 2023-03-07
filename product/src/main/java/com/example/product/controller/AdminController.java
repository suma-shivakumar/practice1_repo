package com.example.product.controller;

import com.example.product.domain.responses.MessageResponse;
import com.example.product.model.UserRolesEntity;
import com.example.product.repository.UserRolesRepository;
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

import java.util.Objects;

@RestController
@Slf4j
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @PostMapping(value = "/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> excelReader(@RequestParam("file") MultipartFile excel,@RequestParam int userId) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            final UserRolesEntity byUserId = userRolesRepository.findByUserId(userId);
//            considerd role_id =1 for admin
            if(!Objects.isNull(byUserId) && !Objects.isNull(byUserId.getRoleId()) && byUserId.getRoleId()==1){
                messageResponse = adminServices.excelReader(excel);
            }else {
                messageResponse.setMessage("Sorry! you don't have admin access");
            }
        } catch (Exception e) {
            log.error("Error occured while updating products list by using excel: {}", e.getMessage());
            return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }
}
