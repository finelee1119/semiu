package com.semiuniv.semiu.entity;

import com.semiuniv.semiu.constant.SubjectType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer id;

    @Column(name = "subject_name", length = 30)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_type")
    private SubjectType subjectType;

    private Integer credit; //이수학점(전공 3학점, 교양1~2학점 등)

    @Column(name = "academic_year")
    private Integer academicYear;

    private Integer semester;

    @Column(name = "day_of_week", length = 5)
    private String dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "max_student")
    private Integer maxStudent; //수강 정원

    @Column(name = "total_student", columnDefinition = "Integer DEFAULT '0'")
    private Integer totalStudent; //현재 학생 수
}
