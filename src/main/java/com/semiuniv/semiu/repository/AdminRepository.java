package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Page<Admin> findById(Integer keyword, Pageable pageable);
    Page<Admin> findByNameContaining(String keyword, Pageable pageable);

}
