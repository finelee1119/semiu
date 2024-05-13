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
@RequestMapping("/semi/notice")
public class NoticeController {
    NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    //등록
    @GetMapping("/insertForm")
    public String insertForm(Model model){
        model.addAttribute("noticeDto", new NoticeDto());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = dateTime.format(formatter); // "yyyy-MM-ddTHH:mm"
        model.addAttribute("currentDateTime", formattedDateTime);
        return "notices/insertNotice";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("noticeDto")NoticeDto dto,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "notices/insertNotice";
        }
        noticeService.insertNotice(dto);
        return "redirect:/semi/notice/show";
    }

    //조회 + 검색
    @GetMapping("/show")
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
        return "notices/showNotice";
    }

    //수정
    @GetMapping("/updateForm/{updateId}")
    public String updateForm(@PathVariable("updateId") Integer id, Model model) {
        NoticeDto noticeDto = noticeService.getNoticeById(id);
        model.addAttribute("noticeDto", noticeDto);
        return "notices/updateNotice";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("noticeDto") NoticeDto dto,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "notices/updateNotice";
        }
        noticeService.updateNotice(dto);
        return "redirect:/semi/notice/show";
    }

    //삭제
    @PostMapping("/delete/{deleteId}")
    public String delete(@PathVariable("deleteId") Integer id){
        noticeService.deleteNotice(id);
        return "redirect:/semi/notice/show";
    }
}
