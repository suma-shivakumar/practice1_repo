package com.example.product.repository;

import com.example.product.model.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity,Integer> {

    @Query(value = "select * from test.products where stocks>0",nativeQuery = true)
    List<ProductsEntity> fetchProuductsWhichAreInStock();

    @Query(value = "select * from test.products where name=?1 and stocks>0;",nativeQuery = true)
    ProductsEntity findByProductsByName(String productName);

    ProductsEntity findByName(String productName);
}
