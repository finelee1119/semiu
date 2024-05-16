package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.service.ProfessorService;
import com.semiuniv.semiu.service.StudentService;
import com.semiuniv.semiu.service.UserDetailService;
import org.springframework.boot.Banner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/semi/find")
public class FindController {

    private final StudentService studentService;
    private final ProfessorService professorService;
    private final UserDetailService userDetailService;
    public FindController(StudentService studentService, ProfessorService professorService, UserDetailService userDetailService) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.userDetailService = userDetailService;
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
    public String findId(String name, LocalDate birth, Model model){


        return "find/studentFindId";
    }
    
    //교수 아이디 찾기
    @GetMapping("/showProfessorId")
    public String showProfessorId(){
        return "find/professorFindId";
    }

    @PostMapping("professorId")
    public String professorId(String name, LocalDate birth, Model model){

        return "find/professorFindId";
    }
    
    //비밀번호 찾기
    @GetMapping("/showPassword")
    public String showPassword(){
        return "find/showPassword";
    }

    @PostMapping("findPassword")
    public String findPassword(@RequestParam("id") Integer id,String name, Model model) {
        // 사용자 ID를 문자열로 변환합니다.
        String userIdString = String.valueOf(id);

        // 사용자 ID를 기반으로 사용자 세부 정보를 조회합니다.
        UserDetails userDetails = userDetailService.loadUserByUsername(userIdString);

        // 조회된 사용자 정보가 null이 아니고, ID와 사용자 이름이 같은지 확인합니다.
        if (userDetails != null && userDetails.getUsername().equals(userIdString)) {
            // 사용자 세부 정보를 모델에 추가하여 뷰에 전달합니다.
            model.addAttribute("userDetails", userDetails);
            // 사용자의 비밀번호 찾기 페이지로 이동합니다.
            return "find/phonePage";
        } else {
            // 사용자 정보를 찾지 못한 경우 또는 ID가 일치하지 않는 경우 처리 로직
            model.addAttribute("error", "ID를 다시 확인해주세요");
            return "errorPage";
        }
    }
}
