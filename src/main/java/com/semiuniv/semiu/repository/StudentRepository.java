package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Page<Student> findById(Integer keyword, Pageable pageable);
    Page<Student> findByNameContaining(String keyword, Pageable pageable);

}
