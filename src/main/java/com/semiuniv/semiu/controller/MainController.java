package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.entity.Admin;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.Users;
import com.semiuniv.semiu.repository.UserRepository;
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
@RequestMapping("/semi")
public class MainController {

    private final StudentService studentService;
    private final ProfessorService professorService;
    private final AdminService adminService;
    private final UserRepository userRepository;

    public MainController(StudentService studentService, ProfessorService professorService, AdminService adminService, UserRepository userRepository) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.adminService = adminService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String login(Principal principal, Model model) {

        // 로그인, 로그아웃후 : Role에 맞는 홈에 이동 > 뒤로 가기 : 위치
        System.out.printf("------------------------------------");
        if(principal == null){
            return"welcome";
        }else{
            String loginId = principal.getName();
            System.out.printf(loginId);
            //로그인 한 User 정보 : Role 포함
            Optional<Users> users = userRepository.findById(Integer.valueOf(loginId));
            String loginRole = String.valueOf(users.get().getRole());

            System.out.printf(loginRole);
            model.addAttribute("loginRole", loginRole);

            //로그인id 정보
            Optional<Student> student = studentService.show_student(Integer.valueOf(loginId));
            Optional<Professor> professor = professorService.show_professor(Integer.valueOf(loginId));
            Optional<Admin> admin = adminService.show_admin(Integer.valueOf(loginId));

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
        }
        return "welcome";
    }

//    @GetMapping("/login-page")
//    public String loginSuccess(Principal principal, Model model){
//        Integer loginId = Integer.valueOf(principal.getName());
//        Optional<Student> student = studentService.show_student(loginId);
//        Optional<Professor> professor = professorService.show_professor(loginId);
//        Optional<Admin> admin = adminService.show_admin(loginId);
//
//        if (student.isPresent()){
//            Student studentLogin = student.get();
//            System.out.println(studentLogin.toString());
//            model.addAttribute("principal", principal.getName());
//            model.addAttribute("studentLogin", studentLogin);
//            return "welcome";
//        } else if (professor.isPresent()) {
//            Professor professorLogin = professor.get();
//            System.out.println(professorLogin.toString());
//            model.addAttribute("principal", principal.getName());
//            model.addAttribute("professorLogin", professorLogin);
//            return "welcome";
//        } else if (admin.isPresent()) {
//            Admin adminLogin = admin.get();
//            System.out.println(adminLogin.toString());
//            model.addAttribute("principal", principal.getName());
//            model.addAttribute("adminLogin", adminLogin);
//            return "welcome";
//        }
//        return "welcome";
//    }
}
