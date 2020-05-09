package com.example.UserApp.repository;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name = "users")
public class UserEntity implements Serializable {


   // private static final long serialVersionUID = -2731425678149216053L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "encryptedPassword")
    private String encryptedPassword;

}
