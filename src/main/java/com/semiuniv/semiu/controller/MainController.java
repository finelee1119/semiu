package com.semiuniv.semiu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {

    @RequestMapping("/semi")
    public String main() {
        return "main";
    }

    // .defaultSuccessUrl : 로그인 성공 시 GetUrl > main.html
    // :로그인 후 다시 main:Index 페이지에 돌아왔을 때 로그인 정보와 같은 학생 정보 가져오기
    @GetMapping("login")
    public String login(Principal principal, Model model){
        model.addAttribute("principal", principal.getName());
///        getName과 같은 정보를 가진 StudentDto가져와서 name으로 보여주기
        return "main";
    }
}
