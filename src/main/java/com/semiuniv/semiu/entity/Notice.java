package com.semiuniv.semiu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Integer id;

    private String title;

    @Lob
    @Column(columnDefinition = "text")
    private String content;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "updated_time")
    private Timestamp updatedTime;

    @PreUpdate
    protected void onUpdate() {
        updatedTime = new Timestamp(System.currentTimeMillis());
    }
}
