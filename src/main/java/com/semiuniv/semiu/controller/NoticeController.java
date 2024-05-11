package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.NoticeDto;
import com.semiuniv.semiu.entity.Notice;
import com.semiuniv.semiu.repository.NoticeRepository;
import com.semiuniv.semiu.service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("semi/notice")
public class NoticeController {
    NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/show")
    public String noticeList(Model model){
        List<NoticeDto> notices = noticeService.findAllNotice();
        model.addAttribute("notices", notices);
        return "/notice/noticeList";
    }

    @GetMapping("/insertForm")
    public String noticeForm(Model model){
        model.addAttribute("noticeDto", new NoticeDto());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = dateTime.format(formatter); // "yyyy-MM-ddTHH:mm"
        model.addAttribute("currentDateTime", formattedDateTime);
        return "/notice/noticeInsert";
    }

    @PostMapping("insertForm")
    public String notice(@Valid @ModelAttribute("noticeDto")NoticeDto noticeDto,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/notice/noticeInsert";
        }
        noticeService.insertNotice(noticeDto);
        return "redirect:/semi/notice/show";
    }

    @PostMapping("/delete")
    public String deleteNotice(@RequestParam("selectedIds")Integer[] selectedIds){
        for (Integer id : selectedIds) {
            noticeService.deleteNotice(id);
        }
        return "redirect:/semi/notice/show";
    }

    @GetMapping("/update/{updateId}")
    public String updateForm(@PathVariable("updateId") Integer id, Model model) {
        NoticeDto noticeDto = noticeService.getNoticeById(id);
        model.addAttribute("noticeDto", noticeDto);
        return "/notice/noticeUpdate";
    }


    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("noticeDto") NoticeDto dto,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/notice/noticeUpdate";
        }
        noticeService.updateNotice(dto);
        return "redirect:/semi/notice/show";
    }
}
