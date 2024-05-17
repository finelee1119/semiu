package com.semiuniv.semiu.dto;

import com.semiuniv.semiu.constant.UserRole;
import com.semiuniv.semiu.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String password;
    private UserRole role;
    private String email;

    public static UserDto fromUserEntity(Users users) {
        return new UserDto(
                users.getId(),
                users.getPassword(),
                users.getRole(),
                users.getEmail()
        );
    }

    public static Users toUserEntity(UserDto dto) {
        Users user = new Users();
        user.setId(dto.getId());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        return user;
    }
}
