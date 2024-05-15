package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.constant.Grade;
import com.semiuniv.semiu.dto.GradeDto;
import com.semiuniv.semiu.dto.StudentGradeDto;
import com.semiuniv.semiu.service.StudentGradeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/semi/grade")
@Slf4j
public class GradeController {
    @Autowired
    StudentGradeService studentGradeService;

    // 학생 로그인 후 학생 성적 조회 화면 진입
    @GetMapping("/{id}")
    public String showStudentGrade(@PathVariable("id") Integer id, Model model) {
        List<GradeDto> gradeDtoList = studentGradeService.getStudentGrades(id);
        model.addAttribute("gradeList", gradeDtoList);
        return "grade/showStudentGrade";
    }

    // 강사 로그인 후 강사 성적 화면 진입
    @GetMapping("professor/{id}")
    public String showGrade(@PathVariable("id") Integer id, Model model) {
//        List<StudentGradeDto> gradeInputList = studentGradeService.findAll();
//        studentGradeService.SubjectGradeInput();
        List<GradeDto> gradeDtoList = studentGradeService.getProfessorGrades(id);
//        model.addAttribute("gradeDto", new StudentGradeDto());
        model.addAttribute("gradeList", gradeDtoList);
//        model.addAttribute("gradeInputList", gradeInputList);
        model.addAttribute("id", id);
        return "grade/showProfessorGrade";
    }

    // 교수 성적 입력, 수정
    @PostMapping("/insertForm/{id}")
    public String insertSubject(@PathVariable("id") Integer id,
                                @RequestParam("studentId") Integer studentId,
                                @RequestParam("subjectId") Integer subjectId,
                                @RequestParam("grade") Grade grade){

        StudentGradeDto studentGradeDto = studentGradeService.findByStudentIdAndSubjectId(studentId, subjectId);
        log.info(studentGradeDto.toString());
        if(studentGradeDto != null) {
            studentGradeDto.setGrade(grade);
            studentGradeService.insertGrade(studentGradeDto);}
        log.info(studentGradeDto.toString());
        return "redirect:/semi/grade/professor" + id;
    }

    // 관리자 성적 데이터 관리
    @GetMapping("/show")
    public String showGrade(Model model) {
        List<GradeDto> gradeDtoList = studentGradeService.findAll();
        model.addAttribute("gradeList", gradeDtoList);
        return "grade/showAdminGrade";
    }

    @PostMapping("/deleteGrade")
    public String deleteGrades(@ModelAttribute("selectedIds") Integer[] selectedIds) {
        for (Integer id : selectedIds) {
        studentGradeService.deleteGrade(id);}
        return "redirect:/semi/grade/show";
    }
}
