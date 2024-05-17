package com.semiuniv.semiu.entity;

import com.semiuniv.semiu.constant.Grade;
import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
public class StudentGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_grade_seq")
//    @SequenceGenerator(name = "student_grade_seq", sequenceName = "student_grade_sequence", allocationSize = 1)
    private Integer no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private Grade grade;
}
