package com.semiuniv.semiu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/semi")
public class HomeController {

    @GetMapping("/admin/home")
    public String adminHome() {
        return "home/adminHome";
    }

    @GetMapping("/student/home")
    public String studentHome() {
        return "home/studentHome";
    }

    @GetMapping("/professor/home")
    public String professorHome() {
        return "home/professorHome";
    }

}
