package com.semiuniv.semiu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/semi")
@RequiredArgsConstructor
public class StudentController {




    //수강 신청 페이지 : username로 이동 (조건 : 로그인 후)
    @GetMapping("application")
    public String lectureApplicationView(Model model){
        return "students/lectureApplication";
    }
}
