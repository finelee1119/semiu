package com.semiuniv.semiu.entity;

import com.semiuniv.semiu.constant.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //db에 저장되는 id

    @Column(name = "user_id", unique = true)
    private String username; //실제로 사용하는 id

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
