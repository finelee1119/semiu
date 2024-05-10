package com.semiuniv.semiu.dto;

import com.semiuniv.semiu.entity.Admin;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    private Integer id; //아이디
    private String name; //성명

    // Entity -> Dto
    public static AdminDto fromAdminEntity(Admin admin) {
        return new AdminDto(
                admin.getId(),
                admin.getName()
        );
    }

    // DTO -> Entity
    public static Admin toAdminEntity(AdminDto dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setName(dto.getName());
        return admin;
    }
}
