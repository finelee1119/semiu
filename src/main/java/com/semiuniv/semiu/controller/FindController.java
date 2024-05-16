package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.dto.UserDto;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.Users;
import com.semiuniv.semiu.repository.UserRepository;
import com.semiuniv.semiu.service.ProfessorService;
import com.semiuniv.semiu.service.StudentService;
import com.semiuniv.semiu.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/semi/find")
public class FindController {

    private final StudentService studentService;
    private final ProfessorService professorService;
    private final UserDetailService userDetailService;
    private final UserRepository userRepository;
    public FindController(StudentService studentService, ProfessorService professorService, UserDetailService userDetailService, UserRepository userRepository) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
    }

    //아이디 찾기
    @GetMapping("/showId")
    public String showId(){
        return "find/showId";
    }
    
    //학생 아이디 찾기
    @GetMapping("/showStudentId")
    public String showStudentId(){
        return "find/studentFindId";
    }

    @PostMapping("studentId")
    public String studentId(@RequestParam("name") String name,
                            @RequestParam("birth") LocalDate birth,
                            @RequestParam("phone") String phone,
                            Model model) {
        Optional<Student> studentName = studentService.findByName(name);

        if (name != null && studentName.isPresent() && studentName.get().getName().equals(name) &&
                studentName.get().getBirth().equals(birth) && studentName.get().getPhone().equals(phone)) {
            model.addAttribute("studentId", studentName.get().getId());
            return "find/studentId";
        }
        model.addAttribute("error", "맞는 정보를 찾을 수 없습니다. 다시 시도해주세요");
        return "error";
    }


    //교수 아이디 찾기
    @GetMapping("/showProfessorId")
    public String showProfessorId(){
        return "find/professorFindId";
    }

    @PostMapping("professorId")
    public String professorId(@RequestParam("name") String name,
                              @RequestParam("birth") LocalDate birth,
                              @RequestParam("phone") String phone,
                              Model model) {
        Optional<Professor> professorOpt = professorService.findByName(name);

        if (professorOpt.isPresent() && professorMatches(professorOpt.get(), name, birth, phone)) {
            model.addAttribute("professorId", professorOpt.get().getId());
            return "find/professorId";
        }

        model.addAttribute("error", "맞는 정보를 찾을 수 없습니다. 다시 시도해주세요");
        return "error";
    }

    private boolean professorMatches(Professor professor, String name, LocalDate birth, String phone) {
        return professor.getName().equals(name) && professor.getBirth().equals(birth) && professor.getPhone().equals(phone);
    }

    //비밀번호 찾기
    @GetMapping("/showPassword")
    public String showPassword(){
        return "find/showPassword";
    }

    @PostMapping("findPassword")
    public String findPassword(@RequestParam("id") Integer id, Model model) {
        String userIdString = String.valueOf(id);
        UserDetails userDetails = userDetailService.loadUserByUsername(userIdString);
        Optional<Users> user = userRepository.findById(userIdString);
        Users userAccount = user.get();
        // 조회된 사용자 정보가 null이 아니고, ID와 사용자 이름이 같은지 확인합니다.
        if (userDetails != null && userDetails.getUsername().equals(userIdString)) {
            userAccount.setPassword(userIdString);
            userRepository.save(userAccount);
            model.addAttribute("password", userDetails.getPassword());
            return "find/password";
        } else {
            model.addAttribute("error", "ID를 다시 확인해주세요");
            return "errorPage";
        }
    }



}
