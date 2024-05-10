package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.GradeDto;
import com.semiuniv.semiu.service.StudentGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/semi/grade")
@RequiredArgsConstructor
public class GradeController {
    @Autowired
    StudentGradeService studentGradeService;

//    @GetMapping("/show")
//    public String showGrade(Model model) {
//        List<GradeDto> gradeDtoList = studentGradeService.findDtoList(id);
//        model.addAttribute("subjects", subjectDto);
//        return "subjects/showSubjectList";
//    }

}
