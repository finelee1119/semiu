package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.entity.Admin;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.service.AdminService;
import com.semiuniv.semiu.service.ProfessorService;
import com.semiuniv.semiu.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
//로그인 페이지로 이동이 안되서 RequestMapping을 위로 올림 +
@RequestMapping("/semi")
public class MainController {

    private final StudentService studentService;
    private final ProfessorService professorService;
    private final AdminService adminService;

    public MainController(StudentService studentService, ProfessorService professorService, AdminService adminService) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.adminService = adminService;
    }

    @GetMapping
    //RequestMapping 주소 : localhost:8080/semi(main) +
    public String login() {
        return "welcome";
    }

    // .defaultSuccessUrl : 로그인 성공 시 GetUrl > main.html
    // :로그인 후 다시 main:Index 페이지에 돌아왔을 때 로그인 정보와 같은 학생 정보 가져오기 +
    @GetMapping("/login-success")
    public String loginSuccess(Principal principal, Model model){
        Integer loginId = Integer.valueOf(principal.getName());
        Optional<Student> student = studentService.show_student(loginId);
        Optional<Professor> professor = professorService.show_professor(loginId);
        Optional<Admin> admin = adminService.show_admin(loginId);

        if (student.isPresent()){
            Student studentLogin = student.get();
            System.out.println(studentLogin.toString());
            model.addAttribute("principal", principal.getName());
            model.addAttribute("studentLogin", studentLogin);
            return "welcome";
        } else if (professor.isPresent()) {
            Professor professorLogin = professor.get();
            System.out.println(professorLogin.toString());
            model.addAttribute("principal", principal.getName());
            model.addAttribute("professorLogin", professorLogin);
            return "welcome";
        } else if (admin.isPresent()) {
            Admin adminLogin = admin.get();
            System.out.println(adminLogin.toString());
            model.addAttribute("principal", principal.getName());
            model.addAttribute("adminLogin", adminLogin);
            return "welcome";
        }
        return "welcome";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
