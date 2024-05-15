package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.dto.GradeDto;
import com.semiuniv.semiu.dto.StudentGradeDto;
import com.semiuniv.semiu.entity.StudentGrade;
import com.semiuniv.semiu.entity.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentGradeRepository extends JpaRepository<StudentGrade, Integer> {
    List<StudentGrade> findBySubjectProfessorId(Integer professorId);

    @Query(value = "SELECT * FROM semiuniv.student_grade where student_id = :studentId and subject_id = :subjectId", nativeQuery = true)
    StudentGrade findByStudentAndSubject(@Param("studentId")Integer studentId, @Param("subjectId")Integer subjectId);

    StudentGrade findBySubjectId(Integer subjectId);
}

