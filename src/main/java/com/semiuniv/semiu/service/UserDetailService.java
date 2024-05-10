package com.semiuniv.semiu.service;


import com.semiuniv.semiu.config.PrincipalDetails;
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

    //비밀번호 암호화
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findById(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        String password = passwordEncoder.encode(user.get().getPassword());
        Users userAccount = user.get();
        userAccount.setPassword(password);
        System.out.println(userAccount);
        return new PrincipalDetails(userAccount);
    }


}
