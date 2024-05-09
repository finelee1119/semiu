package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.NoticeDto;
import com.semiuniv.semiu.entity.Notice;
import com.semiuniv.semiu.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {
    NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public void insertNotice(NoticeDto noticeDto) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Notice notice = noticeDto.fromNoticeDto(noticeDto);
        notice.setCreatedTime(currentTimestamp);

        noticeRepository.save(notice);
    }

    public NoticeDto deleteNotice(Integer id) {
        noticeRepository.deleteById(id);
        return null;
    }

    public NoticeDto getNoticeById(Integer id) {
        return noticeRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public void updateNotice(NoticeDto noticeDto) {
        Notice notice = noticeRepository.findById(noticeDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice Id:" + noticeDto.getId()));
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        noticeRepository.save(notice);
    }
    public List<NoticeDto> findAllNotice() {
        return noticeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private NoticeDto convertToDto(Notice notice) {
        return new NoticeDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedTime()
                );
    }
}
