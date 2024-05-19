package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.constant.UserRole;
import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.entity.Department;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.Users;
import com.semiuniv.semiu.repository.DepartmentRepository;
import com.semiuniv.semiu.service.StudentService;
import com.semiuniv.semiu.service.UserDetailService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/semi/admin/student")
public class StudentController {

    private final DepartmentRepository departmentRepository;
    private final StudentService studentService;
    private final UserDetailService userService;

    public StudentController(StudentService studentService, DepartmentRepository departmentRepository, UserDetailService userService) {
        this.studentService = studentService;
        this.departmentRepository = departmentRepository;
        this.userService = userService;
    }

    //등록
    @GetMapping("/insertForm")
    public String insertForm(Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        model.addAttribute("studentDto", new StudentDto());
        return "students/insertStudent";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("studentDto") StudentDto dto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "students/insertStudent";
        }

        studentService.insertStudent(dto);
        // 학생 등록 시 유저로 등록
        Optional<Student> user = studentService.findByName(dto.getName());
        Users users = new Users();
        users.setId(user.get().getId());
        users.setPassword(String.valueOf(user.get().getId()));
        users.setRole(UserRole.STUDENT);
        userService.createUser(users);
        return "redirect:/semi/admin/student/show";
    }

    //조회 + 검색
    @GetMapping("/show")
    public String showAll(Model model,
                          @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Page<StudentDto> studentDto = null;

        if (keyword == null || keyword.isEmpty()) {
            studentDto = studentService.showAllStudents(pageable);
        } else {
            try {
                int id = Integer.parseInt(keyword);
                // 키워드가 숫자로 변환될 수 있으면 ID로 검색
                studentDto = studentService.searchStudentById(id, pageable);
            } catch (NumberFormatException e) {
                // 숫자로 변환되지 않는 경우 이름으로 검색
                studentDto = studentService.searchStudentByName(keyword, pageable);
            }
        }

        model.addAttribute("studentDto", studentDto);
        return "students/showStudents";
    }

    //수정
    @GetMapping("/updateForm/{updateId}")
    public String updateForm(@PathVariable("updateId") Integer id, Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);

        StudentDto studentDto = studentService.showOneStudent(id);
        model.addAttribute("studentDto", studentDto);
        return "students/updateStudent";
    }

    @PostMapping("/student/update")
    public String update(@Valid @ModelAttribute("studentDto") StudentDto dto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "students/updateStudent";
        }

        studentService.updateStudent(dto);
        return "redirect:/semi/admin/student/show";
    }

    //삭제
    @PostMapping("/delete/{deleteId}")
    public String delete(@Valid @PathVariable("deleteId") Integer id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("deleteFailed", "수강/성적 데이터가 존재하여 삭제할 수 없습니다.");
        }
        return "redirect:/semi/admin/student/show";
    }
}
