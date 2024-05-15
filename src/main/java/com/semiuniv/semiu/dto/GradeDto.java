package com.semiuniv.semiu.dto;

import com.semiuniv.semiu.constant.Grade;
import com.semiuniv.semiu.constant.SubjectType;
import com.semiuniv.semiu.entity.StudentGrade;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {
//    연도	학기	학번	이름	과목명	점수

    // 과목 테이블
    private Integer gradeYear;
    private Integer semester;
    private Integer subjectId;
    private String subjectName;
    private SubjectType subjectType;
    private Integer credit; //이수학점(전공 3학점, 교양1~2학점 등)

    // 학생 테이블
    private Integer studentId;
    private String studentName;
    private String department;
    private Integer academicYear;

    private Integer no;
    private Grade grade;


    public static StudentGrade fromStudentGradeDto(GradeDto dto){
        StudentGrade studentGrade = new StudentGrade();
        studentGrade.getStudent().setId(dto.getStudentId());
        studentGrade.getSubject().setId(dto.getSubjectId());
        studentGrade.setGrade(dto.getGrade());
        return studentGrade;
    }
}

