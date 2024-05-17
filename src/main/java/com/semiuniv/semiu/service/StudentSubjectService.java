package com.semiuniv.semiu.service;

import com.semiuniv.semiu.entity.StudentSubject;
import com.semiuniv.semiu.repository.StudentSubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class StudentSubjectService {
    private final StudentSubjectRepository studentSubjectRepository;


    public void insertApplication(StudentSubject studentSubject) {
        System.out.println("service"+studentSubject.toString());
        studentSubjectRepository.save(studentSubject);
    }



}