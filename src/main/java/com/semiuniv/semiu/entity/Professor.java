package com.semiuniv.semiu.entity;

import com.semiuniv.semiu.constant.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_seq")
    @SequenceGenerator(name = "professor_seq", sequenceName = "professor_sequence", allocationSize = 1)
    @Column(name = "professor_id")
    private Integer id;

    @Column(name = "professor_name", length = 30)
    private String name;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 100)
    private String address;

    @Column(length = 13)
    private String phone;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "termination_date", nullable = true)
    private LocalDate terminationDate;

    @Column(length = 100)
    private String email;
}
