package com.semiuniv.semiu.dto;

import com.semiuniv.semiu.constant.Grade;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.StudentGrade;
import com.semiuniv.semiu.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGradeDto {

    private Integer no;

    private Student student;

    private Subject subject;

    private Grade grade;

    public StudentGradeDto(Student student, Subject subject, Grade grade) {
        this.student = student;
        this.subject = subject;
        this.grade = grade;
    }

    public static StudentGradeDto fromStudentGradeEntity(StudentGrade grade){
        return new StudentGradeDto(
                grade.getStudent(), grade.getSubject(), grade.getGrade());}

    public StudentGrade fromStudentGradeDto(StudentGradeDto dto) {
        StudentGrade grade = new StudentGrade();
        grade.setStudent(dto.getStudent());
        grade.setSubject(dto.getSubject());
        grade.setGrade(dto.getGrade());
        return grade;
    }
}
