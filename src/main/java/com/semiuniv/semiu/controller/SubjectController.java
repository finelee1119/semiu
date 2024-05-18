package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.dto.SubjectDto;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.repository.ClassroomRepository;
import com.semiuniv.semiu.repository.ProfessorRepository;
import com.semiuniv.semiu.service.ProfessorService;
import com.semiuniv.semiu.service.StudentGradeService;
import com.semiuniv.semiu.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/semi/admin/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final ProfessorService professorService;
    private final ClassroomRepository classroomRepository;
    private final StudentGradeService studentGradeService;

    //등록
    @GetMapping("/insertForm")
    public String insertSubjectForm(Model model, Pageable pageable) {
        List<Integer> classrooms = classroomRepository.findAllIds();
        model.addAttribute("classrooms", classrooms);

        Page<ProfessorDto> professors = professorService.showAllProfessors(pageable);
        model.addAttribute("professors", professors);

        SubjectDto subjectDto = new SubjectDto();
        model.addAttribute("subject", subjectDto);
        return "subjects/insertSubject";
    }

    @PostMapping("/insertForm")
    public String insertSubject(@ModelAttribute("subject") SubjectDto subject) {
        subject.setClassroom(subject.getClassroom());
        subject.setTotalStudent(0);
        log.info(subject.toString());
        subjectService.insertSubject(subject);
        return "redirect:/semi/admin/subject/show";
    }

    //조회 + 검색
    @GetMapping("/show")
    public String showSubject(Model model,
                              @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Page<SubjectDto> subjectDto = null;

        if (keyword == null || keyword.isEmpty()) {
            subjectDto = subjectService.findSubject(pageable);
        } else {
            try {
                int id = Integer.parseInt(keyword);
                // 키워드가 숫자로 변환될 수 있으면 강의코드로 검색
                subjectDto = subjectService.searchSubjectById(id, pageable);
            } catch (NumberFormatException e) {
                // 숫자로 변환되지 않는 경우 강의명으로 검색
                subjectDto = subjectService.searchSubjectBySubjectName(keyword, pageable);
            }
        }
        model.addAttribute("selectedIds", new Integer[]{});
        model.addAttribute("subjects", subjectDto);
        return "subjects/showSubjectList";
    }

    //수정
    @GetMapping("/updateSubject")
    public String updateSubjectForm(@RequestParam("updateId") int id, Model model, Pageable pageable) {
        List<Integer> classrooms = classroomRepository.findAllIds();
        model.addAttribute("classrooms", classrooms);

        Page<ProfessorDto> professors = professorService.showAllProfessors(pageable);
        model.addAttribute("professors", professors);

        SubjectDto subject = subjectService.findSubjectId(id);
        model.addAttribute("subject", subject);
        return "subjects/updateSubject";
    }

    @PostMapping("/updateSubject")
    public String updateSubject(@ModelAttribute("subject") SubjectDto subject) {
        subject.setClassroom(subject.getClassroom());
        log.info(subject.toString());
        subjectService.updateSubject(subject);
        return "redirect:/semi/admin/subject/show";
    }

    //삭제
    @PostMapping("/deleteSubjects")
    public String deleteSubjects(@Valid @ModelAttribute("selectedIds") Integer[] selectedIds, RedirectAttributes redirectAttributes) {
        try {
            for (Integer id : selectedIds) {
                subjectService.deleteSubject(id);
            }
        }  catch (Exception e) {
            redirectAttributes.addFlashAttribute("deleteFailed","수강/성적 데이터가 존재하여 삭제가 불가능합니다.");
        }
        return "redirect:/semi/admin/subject/show";
    }

}