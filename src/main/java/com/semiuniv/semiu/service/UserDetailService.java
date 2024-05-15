package com.semiuniv.semiu.service;


import com.semiuniv.semiu.config.PrincipalDetails;
import com.semiuniv.semiu.constant.UserRole;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.Users;
import com.semiuniv.semiu.repository.StudentRepository;
import com.semiuniv.semiu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findById(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Users userAccount = user.get();

        // 번호 대역에 따른 역할 설정
        if (userAccount.getId().toString().startsWith("2024")) {
            userAccount.setRole(UserRole.STUDENT);
        } else if (userAccount.getId().toString().startsWith("24")) {
            userAccount.setRole(UserRole.PROFESSOR);
        } else {
            userAccount.setRole(UserRole.ADMIN);
        }

        // 비밀번호 암호화 설정
        String password = passwordEncoder.encode(user.get().getPassword());
        userAccount.setPassword(password);

        return new PrincipalDetails(userAccount);
    }


}
