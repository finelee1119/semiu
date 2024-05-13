package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.NoticeDto;
import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.entity.Notice;
import com.semiuniv.semiu.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class NoticeService {
    NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    //등록
    public void insertNotice(NoticeDto dto) {
        Notice notice = NoticeDto.toNoticeEntity(dto);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        notice.setCreatedTime(currentTimestamp);
        noticeRepository.save(notice);
    }

    //조회
    public Page<NoticeDto> findAllNotice(Pageable pageable) {
        return noticeRepository.findAll(pageable)
                .map(NoticeDto::fromNoticeEntity);
    }

    public NoticeDto getNoticeById(Integer id) {
        return noticeRepository.findById(id)
                .map(NoticeDto::fromNoticeEntity)
                .orElse(null);
    }

    //검색
    public Page<NoticeDto> searchNoticeByTitle(String title, Pageable pageable) {
        return noticeRepository.findByTitleContaining(title, pageable)
                .map(NoticeDto::fromNoticeEntity);
    }

    //수정
    public void updateNotice(NoticeDto dto) {
        Notice notice = noticeRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice Id:" + dto.getId()));
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        noticeRepository.save(notice);
    }

    //삭제
    public void deleteNotice(Integer id) {
        noticeRepository.deleteById(id);
    }
}
