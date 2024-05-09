package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.entity.Department;
import com.semiuniv.semiu.repository.DepartmentRepository;
import com.semiuniv.semiu.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/semi/professor")
public class ProfessorController {

    private final DepartmentRepository departmentRepository;
    private final ProfessorService professorService;

    public ProfessorController(DepartmentRepository departmentRepository, ProfessorService professorService) {
        this.departmentRepository = departmentRepository;
        this.professorService = professorService;
    }

    //등록
    @GetMapping("/insertForm")
    public String insertForm(Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        model.addAttribute("professorDto", new ProfessorDto());
        return  "professors/insertProfessorForm";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("professorDto") ProfessorDto dto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "professors/insertProfessorForm";
        }

        professorService.insertProfessor(dto);
        return "redirect:/semi/professor/show";
    }

    //조회
    @GetMapping("/show")
    public String showAll(Model model) {
        List<ProfessorDto> professorDtoList = professorService.showAllProfessors();
        model.addAttribute("professorDto", professorDtoList);
        return "professors/showProfessors";
    }

    //수정
    @GetMapping("/updateForm/{updateId}")
    public String updateForm(@PathVariable("updateId") Integer id, Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);

        ProfessorDto professorDto = professorService.showOneProfessor(id);
        model.addAttribute("professorDto", professorDto);
        return "professors/updateProfessorForm";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("professorDto") ProfessorDto dto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "professors/updateProfessorForm";
        }

        professorService.updateProfessor(dto);
        return "redirect:/semi/professor/show";
    }

    //삭제
    @PostMapping("/delete/{deleteId}")
    public String delete(@PathVariable("deleteId") Integer id) {
        professorService.deleteProfessor(id);
        return "redirect:/semi/professor/show";
    }
}
