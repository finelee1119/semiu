package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.constant.Grade;
import com.semiuniv.semiu.dto.GradeDto;
import com.semiuniv.semiu.dto.StudentGradeDto;
import com.semiuniv.semiu.service.StudentGradeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/semi")
@Slf4j
public class GradeController {
    @Autowired
    StudentGradeService studentGradeService;

    // 학생 로그인 후 학생 성적 조회 화면 진입
    @GetMapping("/grade/{id}")
    public String showStudentGrade(@PathVariable("id") Integer id, Model model,
                                   @PageableDefault(page = 0, size = 10, sort = "no", direction = Sort.Direction.ASC) Pageable pageable,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<GradeDto> gradeDtoList = null;
        if (keyword == null || keyword.isEmpty()) {
            gradeDtoList = studentGradeService.getStudentGrades(id, pageable);
        }else {
            try {
                int key = Integer.parseInt(keyword);
                gradeDtoList = studentGradeService.searchSubjectById(key, pageable);
            } catch (NumberFormatException e) {
            }
        }
        log.info(gradeDtoList.toString());
        model.addAttribute("gradeList", gradeDtoList);
        return "grade/showStudentGrade";
    }


    // 강사 로그인 후 강사 성적 화면 진입
    @GetMapping("/grade/professor/{id}")
    public String showGrade(@PathVariable("id") Integer id, Model model,
                            @PageableDefault(page = 0, size = 10, sort = "no", direction = Sort.Direction.ASC) Pageable pageable,
                            @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<GradeDto> gradeDtoList = null;
        if (keyword == null || keyword.isEmpty()) {
            gradeDtoList = studentGradeService.getProfessorGrades(id, pageable);
        }else {
            try {
                int key = Integer.parseInt(keyword);
                // subjectId로 검색 시도
                gradeDtoList = studentGradeService.searchSubjectById(key, pageable);
                if (gradeDtoList.isEmpty()) {
                    // subjectId로 검색한 결과가 없을 경우 studentId로 검색 시도
                    gradeDtoList = studentGradeService.searchStudentById(key, pageable);
                }
            } catch (NumberFormatException e) {
                // keyword가 숫자가 아닐 경우 studentId로 검색
                gradeDtoList = studentGradeService.searchStudentById(Integer.parseInt(keyword), pageable);
            }
        }
        model.addAttribute("gradeList", gradeDtoList);
        model.addAttribute("id", id);
        return "grade/showProfessorGrade";
    }

    // 교수 성적 입력, 수정
    @PostMapping("/grade/insertForm/{id}")
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
        return "redirect:/semi/grade/professor/" + id;
    }

    // 관리자 성적 데이터 관리
    @GetMapping("/admin/grade/show")
    public String showGrade(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "no", direction = Sort.Direction.ASC) Pageable pageable,
                            @RequestParam(value = "keyword", defaultValue = "") String keyword){
        Page<GradeDto> gradeDtoList = null;
        if (keyword == null || keyword.isEmpty()) {
            gradeDtoList = studentGradeService.findAll(pageable);
        } else {
            try {
                int id = Integer.parseInt(keyword);
                // subjectId로 검색 시도
                gradeDtoList = studentGradeService.searchSubjectById(id, pageable);
                if (gradeDtoList.isEmpty()) {
                    // subjectId로 검색한 결과가 없을 경우 studentId로 검색 시도
                    gradeDtoList = studentGradeService.searchStudentById(id, pageable);
                }
            } catch (NumberFormatException e) {
                // keyword가 숫자가 아닐 경우 studentId로 검색
                gradeDtoList = studentGradeService.searchStudentById(Integer.parseInt(keyword), pageable);
            }
        }
        model.addAttribute("gradeList", gradeDtoList);
        return "grade/showAdminGrade";
    }

    @PostMapping("admin/grade/deleteGrade")
    public String deleteGrades(@RequestParam("selectedIds") Integer[] selectedIds) {
        log.info(Arrays.toString(selectedIds));
        for (Integer id : selectedIds) {
            studentGradeService.deleteGrade(id);}
        return "redirect:/semi/admin/grade/show";
    }
}
