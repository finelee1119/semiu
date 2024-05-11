package com.semiuniv.semiu.dto;

import com.semiuniv.semiu.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private Integer id;
    private String title;
    private String content;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    public static NoticeDto fromNoticeEntity(Notice notice){
        return new NoticeDto(
                notice.getId(), notice.getTitle(), notice.getContent(), notice.getCreatedTime(), notice.getUpdatedTime()
        );
    }

    public Notice fromNoticeDto(NoticeDto noticeDto) {
        Notice notice = new Notice();
        notice.setId(noticeDto.getId());
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setCreatedTime(noticeDto.getCreatedTime());
        notice.setUpdatedTime(noticeDto.getUpdatedTime());
        return notice;
    }

}
