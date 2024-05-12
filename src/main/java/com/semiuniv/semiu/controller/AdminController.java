package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.AdminDto;
import com.semiuniv.semiu.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/semi/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //등록
    @GetMapping("/insertForm")
    public String insertForm(Model model) {
        model.addAttribute("adminDto", new AdminDto());
        return "admins/insertAdminForm";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("adminDto") AdminDto dto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admins/insertAdminForm";
        }

        adminService.insertAdmin(dto);
        return "redirect:/semi/admin/show";
    }

    //조회 + 검색
    @GetMapping("/show")
    public String showAll(Model model,
                          @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Page<AdminDto> adminDto = null;

        if (keyword == null || keyword.isEmpty()) {
            adminDto = adminService.showAllAdmins(pageable);
        } else {
            try {
                int id = Integer.parseInt(keyword);
                // 키워드가 숫자로 변환될 수 있으면 ID로 검색
                adminDto = adminService.searchAdminById(id, pageable);
            } catch (NumberFormatException e) {
                // 숫자로 변환되지 않는 경우 이름으로 검색
                adminDto = adminService.searchAdminByName(keyword, pageable);
            }
        }

        model.addAttribute("adminDto", adminDto);
        return "admins/showAdmins";
    }

    //수정
    @GetMapping("/updateForm/{updateId}")
    public String updateForm(@PathVariable("updateId") Integer id, Model model) {
        AdminDto adminDto = adminService.showOneAdmin(id);
        model.addAttribute("adminDto", adminDto);
        return "admins/updateAdminForm";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("adminDto") AdminDto dto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admins/updateAdminForm";
        }

        adminService.updateAdmin(dto);
        return "redirect:/semi/admin/show";
    }

    //삭제
    @PostMapping("/delete/{deleteId}")
    public String delete(@PathVariable("deleteId") Integer id) {
        adminService.deleteAdmin(id);
        return "redirect:/semi/admin/show";
    }
}
