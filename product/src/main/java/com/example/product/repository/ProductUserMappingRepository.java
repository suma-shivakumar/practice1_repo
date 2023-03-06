package com.example.product.repository;

import com.example.product.model.ProductUserMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductUserMappingRepository extends JpaRepository<ProductUserMappingEntity,Integer> {
}
