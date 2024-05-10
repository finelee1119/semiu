package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.Subject;
import com.semiuniv.semiu.service.StudentService;
import com.semiuniv.semiu.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/semi")
@RequiredArgsConstructor
public class ApplicationController {
    private final StudentService studentService;
    private final SubjectService subjectService;

    //수강 신청 페이지 : 로그인 전
    @GetMapping("application")
    public String lectureApplicationView(Model model){
        //신청 과목
        List<Subject> subject = subjectService.showSubject();
        model.addAttribute("subject", subject);
        return "application/lectureApplication";
    }

    //수강 신청 페이지 : username로 이동 (조건 : 로그인 후)
    @GetMapping("application/{id}")
    public String lectureApplicationLoginView(@PathVariable("id") Integer id, Model model){
        //로그인한 학생 정보
        Optional<Student> studentInfo = studentService.findById(id);
        Student studentLogin = studentInfo.get();
        model.addAttribute("studentLogin", studentLogin);
        //신청 과목
        List<Subject> subject = subjectService.showSubject();
        model.addAttribute("subject", subject);
        return "application/lectureApplication";
    }
}
