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
    private final StudentRepository studentRepository;

    //비밀번호 암호화
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetailService(UserRepository userRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> userStudent = studentRepository.findById(Integer.valueOf(username)); //에러 나서 일단 Integer로 감싸놨어요
        Optional<Users> user = userRepository.findById(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        String password = passwordEncoder.encode(user.get().getPassword());
        Users userAccount = user.get();
        userAccount.setPassword(password);
        Student student = userStudent.get();
        System.out.println(student);
        System.out.println(userAccount);
        return new PrincipalDetails(userAccount);
    }
}
