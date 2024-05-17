package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.dto.UserDto;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Users;
import com.semiuniv.semiu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void updatePassword(UserDto dto) {
        Users users = UserDto.toUserEntity(dto);
        userRepository.save(users);
    }
}
