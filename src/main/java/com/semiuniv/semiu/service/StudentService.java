package com.semiuniv.semiu.service;

import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.repository.Student1Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    private final Student1Repository studentRepository;

    public List<Student> findStudents() {
        return studentRepository.findAll();
    }
}
