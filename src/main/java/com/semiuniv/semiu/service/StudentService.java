package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //등록
    public void insertStudent(StudentDto dto) {
        Student student = StudentDto.toStudentEntity(dto);
        studentRepository.save(student);
    }

    //조회
    public Page<StudentDto> showAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(StudentDto::fromStudentEntity);
    }

    public StudentDto showOneStudent(Integer id) {
        return studentRepository.findById(id)
                .map(StudentDto::fromStudentEntity)
                .orElse(null);
    }

    //검색
    public Page<StudentDto> searchStudentById(Integer id, Pageable pageable) {
        return studentRepository.findById(id, pageable)
                .map(StudentDto::fromStudentEntity);
    }

    public Page<StudentDto> searchStudentByName(String name, Pageable pageable) {
        return studentRepository.findByNameContaining(name, pageable)
                .map(StudentDto::fromStudentEntity);
    }

    //수정
    public void updateStudent(StudentDto dto) {
        Student student = StudentDto.toStudentEntity(dto);
        studentRepository.save(student);
    }

    //삭제
    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }


    //로그인 후 학생 정보
    public Optional<Student> show_student(Integer loginId) {
        return studentRepository.findById(loginId);
    }

    //수강 신청 페이지 이동 시 가져올 로그인 한 학생 정보 : 수강 신청 +
    public Optional<Student> findById(Integer id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> findByName(String name) {
        return studentRepository.findByName(name);
    }

    public Optional<Student> findByBirth(LocalDate birth) {
        return studentRepository.findByBirth(birth);
    }

    public Optional<Student> findByPhone(String phone) {
        return studentRepository.findByPhone(phone);
    }
}
