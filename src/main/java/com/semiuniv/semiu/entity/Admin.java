package com.semiuniv.semiu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Admin {

    @Id
    @Column(name = "admin_id")
    private Integer id;

    @Column(name = "admin_name", length = 30)
    private String name;

    @Column(length = 100)
    private String email;
}
