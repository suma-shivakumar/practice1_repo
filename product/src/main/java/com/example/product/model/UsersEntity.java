package com.example.product.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id",unique = true,nullable = false)
    private String emailId;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "phone_number")
    private Integer phoneNumber;

    @Column(name = "wallet")
    private int wallet;
}
