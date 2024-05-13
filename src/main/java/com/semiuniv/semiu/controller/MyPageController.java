package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.service.ProfessorService;
import com.semiuniv.semiu.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("/semi/my")
public class MyPageController {

    final StudentService studentService;
    final ProfessorService professorService;

    public MyPageController(StudentService studentService, ProfessorService professorService) {
        this.studentService = studentService;
        this.professorService = professorService;
    }

    @GetMapping("/student")
    public String showStudent(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Integer userId = Integer.valueOf(username);
            Optional<Student> studentOptional = studentService.show_student(userId);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                StudentDto studentDto = StudentDto.fromStudentEntity(student);
                model.addAttribute("studentDto", studentDto);
            } else {
                model.addAttribute("errorMessage", "해당 학생을 찾을 수 없습니다.");
            }
        } else {
            model.addAttribute("errorMessage", "로그인한 사용자 정보를 가져올 수 없습니다.");
        }
        return "my/studentPage";
    }

    @GetMapping("/professor")
    public String showProfessor(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Integer userId = Integer.valueOf(username);
            Optional<Professor> professorOptional = professorService.show_professor(userId);
            if (professorOptional.isPresent()) {
                Professor professor = professorOptional.get();
                ProfessorDto professorDto = ProfessorDto.fromProfessorEntity(professor);
                model.addAttribute("professorDto", professorDto);
            } else {
                model.addAttribute("errorMessage", "해당 학생을 찾을 수 없습니다.");
            }
        } else {
            model.addAttribute("errorMessage", "로그인한 사용자 정보를 가져올 수 없습니다.");
        }
        return "my/professorPage";
    }
}
