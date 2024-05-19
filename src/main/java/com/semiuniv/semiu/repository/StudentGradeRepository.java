package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.dto.GradeDto;
import com.semiuniv.semiu.dto.StudentGradeDto;
import com.semiuniv.semiu.entity.StudentGrade;
import com.semiuniv.semiu.entity.StudentSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentGradeRepository extends JpaRepository<StudentGrade, Integer> {
    Page<StudentGrade> findBySubjectProfessorId(Integer professorId, Pageable pageable);

    @Query(value = "SELECT * FROM student_grade where student_id = :studentId and subject_id = :subjectId", nativeQuery = true)
    StudentGrade findByStudentAndSubject(@Param("studentId")Integer studentId, @Param("subjectId")Integer subjectId);

    StudentGrade findBySubjectId(Integer subjectId);

    StudentGrade findByNo(Integer id);


    Page<StudentGrade> findBySubjectId(Integer keyword, Pageable pageable);
    Page<StudentGrade> findBySubjectNameContaining(String keyword, Pageable pageable);

    Page<StudentGrade> findByStudentId(Integer keyword, Pageable pageable);

}

