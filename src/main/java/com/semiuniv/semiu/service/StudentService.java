package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.entity.Department;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.repository.DepartmentRepository;
import com.semiuniv.semiu.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
    }

    //등록
    public void insertStudent(StudentDto dto) {
        Student student = StudentDto.toStudentEntity(dto);
        studentRepository.save(student);
    }

    //조회
    public List<StudentDto> showAllStudents() {
        List<StudentDto> studentDtoList = new ArrayList<>();
        return studentRepository.findAll()
                .stream()
                .map(StudentDto::fromStudentEntity)
                .toList();
    }

    public StudentDto showOneStudent(Integer id) {
        return studentRepository.findById(id)
                .map(StudentDto::fromStudentEntity)
                .orElse(null);
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

    //로그인 후 학생 정보 : main.html +
    public Optional<Student> show_student(Integer loginId) {
        return studentRepository.findById(loginId);
    }
    //수강 신청 페이지 이동 시 가져올 로그인 한 학생 정보 : 수강 신청 +
    public Optional<Student> findById(Integer id) {
        return studentRepository.findById(id);
    }
}
