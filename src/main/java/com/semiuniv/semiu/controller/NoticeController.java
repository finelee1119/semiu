package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.NoticeDto;
import com.semiuniv.semiu.service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/semi")
public class NoticeController {
    NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    //등록
    @GetMapping("/admin/notice/insertForm")
    public String insertForm(Model model){
        model.addAttribute("noticeDto", new NoticeDto());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = dateTime.format(formatter); // "yyyy-MM-ddTHH:mm"
        model.addAttribute("currentDateTime", formattedDateTime);
        return "notices/insertNotice";
    }

    @PostMapping("/admin/notice/insert")
    public String insert(@Valid @ModelAttribute("noticeDto")NoticeDto dto,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "notices/insertNotice";
        }
        noticeService.insertNotice(dto);
        return "redirect:/semi/notice/show";
    }

    //조회 + 검색
    // 공지사항 글 목록 ( 관리자 사용)
    @GetMapping("/admin/notice/showAll")
    public String showAll(Model model,
                          @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword){

        Page<NoticeDto> noticeDto = null;

        if (keyword == null || keyword.isEmpty()) {
            noticeDto = noticeService.findAllNotice(pageable);
        } else {
            noticeDto = noticeService.searchNoticeByTitle(keyword, pageable);
        }

        model.addAttribute("noticeDto", noticeDto);
        return "notices/showNoticeAdmin";
    }

    // 공지사항 글 목록 ( 학생/교수 사용)
    @GetMapping("/notice/show")
    public String show(Model model,
                          @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword){

        Page<NoticeDto> noticeDto = null;

        if (keyword == null || keyword.isEmpty()) {
            noticeDto = noticeService.findAllNotice(pageable);
        } else {
            noticeDto = noticeService.searchNoticeByTitle(keyword, pageable);
        }

        model.addAttribute("noticeDto", noticeDto);
        return "notices/showNotice";
    }

    // 공지사항 글 디테일 ( 학생/교수/관리자 공통 사용 )
    @GetMapping("/notice/show/detail")
    public String showDetail(@RequestParam("id") Integer id, Model model) {
        NoticeDto noticeDto = noticeService.getNoticeById(id);

        if (noticeDto == null) {
            return "redirect:/semi/notice/show";
        }

        model.addAttribute("noticeDto", noticeDto);
        return "notices/showNoticeDetail";
    }

    //수정
    @GetMapping("/admin/notice/updateForm/{updateId}")
    public String updateForm(@PathVariable("updateId") Integer id, Model model) {
        NoticeDto noticeDto = noticeService.getNoticeById(id);
        model.addAttribute("noticeDto", noticeDto);
        return "notices/updateNotice";
    }

    @PostMapping("/admin/notice/update")
    public String update(@Valid @ModelAttribute("noticeDto") NoticeDto dto,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "notices/updateNotice";
        }
        noticeService.updateNotice(dto);
        return "redirect:/semi/admin/notice/showAll";
    }

    //삭제
    @PostMapping("/notice/delete/{deleteId}")
    public String delete(@PathVariable("deleteId") Integer id){
        noticeService.deleteNotice(id);
        return "redirect:/semi/notice/show";
    }
}
