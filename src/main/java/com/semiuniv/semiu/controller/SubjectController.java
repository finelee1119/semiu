package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.dto.SubjectDto;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.repository.ClassroomRepository;
import com.semiuniv.semiu.repository.ProfessorRepository;
import com.semiuniv.semiu.service.ProfessorService;
import com.semiuniv.semiu.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/semi/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final ProfessorService professorService;
    private final ClassroomRepository classroomRepository;


    @GetMapping("/show")
    public String showSubject(Model model) {
        List<SubjectDto> subjectDto = subjectService.findSubject();
        model.addAttribute("subjects", subjectDto);
        return "subjects/showSubjectList";
    }

    @GetMapping("/insertForm")
    public String insertSubjectForm(Model model){
        List<Integer> classrooms = classroomRepository.findAllIds();
        model.addAttribute("classrooms", classrooms);
        List<ProfessorDto> professors = professorService.showAllProfessors();
        model.addAttribute("professors", professors);
        // 새로운 Subject 객체를 생성하고 모델에 추가합니다.
        SubjectDto subjectDto = new SubjectDto();
        model.addAttribute("subject", subjectDto);
        return "subjects/insertSubject";
    }

    @PostMapping("/insertForm")
    public String insertSubject (@ModelAttribute("subject") SubjectDto subject){
//        // 사용자 입력 : 교수 이름, subject 테이블 내 professor_id 저장을 위한 코드
//        Professor professor = professorRepository.findByName(subject.getProfessor().getName());
//        subject.setProfessor(professor);
        subject.setClassroom(subject.getClassroom());
        log.info(subject.toString());
        subjectService.insertSubject(subject);
        return "redirect:/semi/subject/show";
    }

    @GetMapping("/updateSubject")
    public String updateSubjectForm(@RequestParam("updateId")int id, Model model) {
        List<Integer> classrooms = classroomRepository.findAllIds();
        model.addAttribute("classrooms", classrooms);
        List<ProfessorDto> professors = professorService.showAllProfessors();
        model.addAttribute("professors", professors);
        // 새로운 Subject 객체를 생성하고 모델에 추가합니다.
        SubjectDto subject = subjectService.findSubjectId(id);
        model.addAttribute("subject", subject);
        return "subjects/updateSubject";
    }
    @PostMapping("/updateSubject")
    public String updateSubject (@ModelAttribute("subject") SubjectDto subject){
        subject.setClassroom(subject.getClassroom());
        log.info(subject.toString());
        subjectService.updateSubject(subject);
        return "redirect:/semi/subject/show";
    }

    @PostMapping("/deleteSubjects")
    public String deleteSubjects(@RequestParam("selectedIds") Integer[] selectedIds) {
        for (Integer id : selectedIds) {
            subjectService.deleteSubject(id);
        }
        return "redirect:/semi/subject/show";
    }
}
