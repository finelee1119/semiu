package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Page<Subject> findById(Integer keyword, Pageable pageable);
    Page<Subject> findByNameContaining(String keyword, Pageable pageable);

    List<Subject> findByIdNotIn(List<Integer> subjectIds);

    List<Subject> findByIdIn(List<Integer> subjectIds);

}
