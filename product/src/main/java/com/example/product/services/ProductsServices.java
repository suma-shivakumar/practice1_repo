package com.example.product.services;

import com.example.product.model.ProductsEntity;
import com.example.product.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServices {

    @Autowired
    private ProductsRepository productsRepository;

    public List<ProductsEntity> fetchAllProducts() {
        return productsRepository.findAll();
    }

    public List<ProductsEntity> fetchProductsForUserBasedOnStocks() {
        return productsRepository.fetchProuductsWhichAreInStock();
    }

    public ProductsEntity findByProductName(String productName) {
        return productsRepository.findByName(productName);
    }


}
