package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Page<Student> findById(Integer keyword, Pageable pageable);
    Page<Student> findByNameContaining(String keyword, Pageable pageable);

    Optional<Student> findByName(String name);

    Optional<Student> findByBirth(LocalDate birth);

    Optional<Student> findByPhone(String phone);
}
