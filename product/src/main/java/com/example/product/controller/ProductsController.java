package com.example.product.controller;

import com.example.product.model.ProductsEntity;
import com.example.product.services.ProductsServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ProductsController {

    @Autowired
    private ProductsServices productsServices;

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductsEntity>> findAllProducts() {
        List<ProductsEntity> response = new ArrayList<>();
        try {
            response = productsServices.fetchAllProducts();
        } catch (Exception e) {
            log.error("Error occured fetching all products: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getProductsForUsersBasedOnStocks")
    public ResponseEntity<List<ProductsEntity>> getProductsForUserBasedOnStocks() {
        List<ProductsEntity> response = new ArrayList<>();
        try {
            response = productsServices.fetchProductsForUserBasedOnStocks();
        } catch (Exception e) {
            log.error("Error occured fetching all products: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/searchSpecificProduct/{productName}")
    public ResponseEntity<ProductsEntity> findProductByName(@PathVariable String productName) {
        ProductsEntity productsEntity = new ProductsEntity();
        try {
            productsEntity = productsServices.findByProductName(productName);
        } catch (Exception e) {
            log.error("Error occured fetching product based on name: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productsEntity, HttpStatus.OK);
    }
}
