package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.Users;
import com.semiuniv.semiu.service.ProfessorService;
import com.semiuniv.semiu.service.StudentService;
import com.semiuniv.semiu.service.UserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/semi")
public class HomeController {
    public final ProfessorService professorService;
    public final StudentService studentService;

    public HomeController(ProfessorService professorService, StudentService studentService) {
        this.professorService = professorService;
        this.studentService = studentService;
    }

    @GetMapping("/login-page")
    public String loginPage(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return "index";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "home/adminHome";
    }

    @GetMapping("/student/home")
    public String studentHome(Model model,
                              Principal principal) {
        String user = principal.getName();
        StudentDto student = studentService.showOneStudent(Integer.valueOf(user));
        model.addAttribute("Student", student);
        model.addAttribute("users", user);
        return "home/studentHome";
    }

    @GetMapping("/professor/home")
    public String professorHome(Model model,
                                Principal principal) {
        String user = principal.getName();
        ProfessorDto professor = professorService.showOneProfessor(Integer.valueOf(user));
        model.addAttribute("professor", professor);
        model.addAttribute("users", user);
        return "home/professorHome";
    }


    //공지사항 헤더 로고 홈페이지 이동용
    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Integer userId = Integer.valueOf(username);
            if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("STUDENT"))) {
                Optional<Student> studentOptional = studentService.show_student(userId);
                if (studentOptional.isPresent()) {
                    return "redirect:/semi/student/home";
                }
            } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("PROFESSOR"))) {
                Optional<Professor> professorOptional = professorService.show_professor(userId);
                if (professorOptional.isPresent()) {
                    return "redirect:/semi/professor/home";
                }
            }else {
                return "redirect:/semi/admin/home";
            }
        }
        return "errorPage";
    }
}