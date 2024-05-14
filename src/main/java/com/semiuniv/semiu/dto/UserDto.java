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

    private Integer id; //db에 저장되는 id
    private String username; //실제로 사용하는 id
    private String password;
    private UserRole role;

    public static UserDto fromUserEntity(Users users) {
        return new UserDto(
                users.getId(),
                users.getUsername(),
                users.getPassword(),
                users.getRole()
        );
    }

    public static Users toUserEntity(UserDto dto) {
        Users user = new Users();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }
}
