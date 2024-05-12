package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    Page<Professor> findById(Integer keyword, Pageable pageable);
    Page<Professor> findByNameContaining(String keyword, Pageable pageable);

}
