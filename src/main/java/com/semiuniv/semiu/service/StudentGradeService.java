package com.semiuniv.semiu.service;

import com.semiuniv.semiu.repository.StudentGradeRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentGradeService {

    @Autowired
    EntityManager em;
    @Autowired
    StudentGradeRepository studentGradeRepository;


}
