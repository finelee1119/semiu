package com.semiuniv.semiu.entity;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
public class StudentSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_subject_seq")
//    @SequenceGenerator(name = "student_subject_seq", sequenceName = "student_subject_sequence", allocationSize = 1)
    private Integer no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
