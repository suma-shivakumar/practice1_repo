package com.example.product.repository;

import com.example.product.model.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRolesEntity, Integer> {
    List<UserRolesEntity> findByRoleId(int i);

    UserRolesEntity findByUserId(int userId);

}
