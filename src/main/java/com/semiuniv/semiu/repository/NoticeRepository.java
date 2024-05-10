package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.dto.NoticeDto;
import com.semiuniv.semiu.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
