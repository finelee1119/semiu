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

    public static NoticeDto fromNoticeEntity(Notice notice){
        return new NoticeDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedTime()
        );
    }

    public static Notice toNoticeEntity(NoticeDto dto) {
        Notice notice = new Notice();
        notice.setId(dto.getId());
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setCreatedTime(dto.getCreatedTime());
        return notice;
    }

}
