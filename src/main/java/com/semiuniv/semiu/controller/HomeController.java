package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.entity.Users;
import com.semiuniv.semiu.service.UserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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
    public String studentHome(Model model,
                              Principal principal) {
        model.addAttribute("users", principal.getName());
        return "home/studentHome";
    }

    @GetMapping("/professor/home")
    public String professorHome(Model model,
                                Principal principal) {
        model.addAttribute("users", principal.getName());
        return "home/professorHome";
    }

}
