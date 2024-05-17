package com.semiuniv.semiu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "college_seq")
    @SequenceGenerator(name = "college_seq", sequenceName = "college_sequence", allocationSize = 1)
    @Column(name = "college_id")
    private Integer id;

    @Column(name = "college_name", length = 30, unique = true)
    private String name;
}
