package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.constant.UserRole;
import com.semiuniv.semiu.dto.NoticeDto;
import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.dto.UserDto;
import com.semiuniv.semiu.entity.Department;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.Users;
import com.semiuniv.semiu.repository.DepartmentRepository;
import com.semiuniv.semiu.service.EmailService;
import com.semiuniv.semiu.service.ProfessorService;
import com.semiuniv.semiu.service.StudentService;
import com.semiuniv.semiu.service.UserDetailService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
@Slf4j
@Controller
@RequestMapping("/semi/profile")
public class MyPageController {

    private final DepartmentRepository departmentRepository;
    final StudentService studentService;
    final ProfessorService professorService;

    private final EmailService emailService;

    public MyPageController(DepartmentRepository departmentRepository, StudentService studentService, ProfessorService professorService, UserDetailService userDetailService, EmailService emailService, UserDetailService userDetailService1) {
        this.departmentRepository = departmentRepository;
        this.studentService = studentService;
        this.professorService = professorService;
        this.emailService = emailService;
    }

    @GetMapping("/show")
    public String showProfile(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Integer userId = Integer.valueOf(username);
            if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("STUDENT"))) {
                Optional<Student> studentOptional = studentService.show_student(userId);
                if (studentOptional.isPresent()) {
                    Student student = studentOptional.get();
                    StudentDto studentDto = StudentDto.fromStudentEntity(student);
                    model.addAttribute("studentDto", studentDto);
                    return "profile/studentPage";
                } else {
                    model.addAttribute("errorMessage", "해당 학생을 찾을 수 없습니다.");
                }
            } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("PROFESSOR"))) {
                Optional<Professor> professorOptional = professorService.show_professor(userId);
                if (professorOptional.isPresent()) {
                    Professor professor = professorOptional.get();
                    ProfessorDto professorDto = ProfessorDto.fromProfessorEntity(professor);
                    model.addAttribute("professorDto", professorDto);
                    return "profile/professorPage";
                } else {
                    model.addAttribute("errorMessage", "해당 교수를 찾을 수 없습니다.");
                }
            } else {
                model.addAttribute("errorMessage", "학생 또는 교수로 로그인한 사용자만 프로필을 확인할 수 있습니다.");
            }
        } else {
            model.addAttribute("errorMessage", "로그인한 사용자 정보를 가져올 수 없습니다.");
        }
        return "errorPage";
    }

    // 학생 마이페이지 수정
    @GetMapping("/updateForm/{updateId}")
    public String updateForm(@PathVariable("updateId") Integer id, Model model) {
        StudentDto studentDto = studentService.showOneStudent(id);
        model.addAttribute("studentDto", studentDto);
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "profile/updateStudentProfile";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("studentDto") StudentDto studentDto,
                         @Valid @ModelAttribute("userDto") UserDto userDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile/updateStudentProfile";
        }
        userDto.setId(studentDto.getId());
        studentService.updateStudent(studentDto);
        emailService.updatePassword(userDto);
        return "redirect:/semi/profile/show";
    }

    //교수 마이페이지 수정
    @GetMapping("/updateProfileForm/{updateId}")
    public String updateProfileForm(@PathVariable("updateId") Integer id, Model model) {
        ProfessorDto professorDto = professorService.showOneProfessor(id);
        model.addAttribute("professorDto", professorDto);
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "profile/professorProfileUpdate";
    }

    @PostMapping("updateProfile")
    public String updateProfile(@Valid @ModelAttribute("professorDto") ProfessorDto professorDto,
                                @Valid @ModelAttribute("userDto") UserDto userDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile/professorProfileUpdate";
        }
        userDto.setId(professorDto.getId());
        professorService.updateProfessor(professorDto);
        emailService.updatePassword(userDto);
        return "redirect:/semi/profile/show";
    }
}