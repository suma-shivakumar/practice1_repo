package com.example.product.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_roles")
public class UserRolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private int userRoleId;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "user_id")
    private int userId;
}