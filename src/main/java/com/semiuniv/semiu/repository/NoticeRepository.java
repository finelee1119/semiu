package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    Page<Notice> findByTitleContaining(String keyword, Pageable pageable);

}
