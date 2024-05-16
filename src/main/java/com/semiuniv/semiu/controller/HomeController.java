package com.semiuniv.semiu.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/semi")
public class HomeController {

    @GetMapping("/login-page")
    public String loginPage(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return "welcome";
    }

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
