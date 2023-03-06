package com.example.product.repository;

import com.example.product.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {
   boolean existsByEmailId(String email);

    UsersEntity findByEmailId(String emailId);
}
