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
@RequestMapping("notice")
public class NoticeController {
    NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("")
    public String noticeList(Model model){
        List<NoticeDto> notices = noticeService.findAllNotice();
        model.addAttribute("notices", notices);
        return "/students/noticeList";
    }

    @GetMapping("/insert")
    public String noticeForm(Model model){
        model.addAttribute("noticeDto", new NoticeDto());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = dateTime.format(formatter); // "yyyy-MM-ddTHH:mm"
        model.addAttribute("currentDateTime", formattedDateTime);
        return "/students/noticeInsert";
    }

    @PostMapping("insert")
    public String notice(@Valid @ModelAttribute("noticeDto")NoticeDto noticeDto){
        noticeService.insertNotice(noticeDto);
        return "redirect:/notice";
    }

    @PostMapping("/delete/{deleteId}")
    public String deleteNotice(@PathVariable("deleteId")Integer id){
        noticeService.deleteNotice(id);
        return "redirect:/notice";
    }

    @GetMapping("/update/{updateId}")
    public String updateForm(@PathVariable("updateId") Integer id, Model model) {
        NoticeDto noticeDto = noticeService.getNoticeById(id);
        model.addAttribute("noticeDto", noticeDto);
        return "/students/noticeUpdate";
    }


    @PostMapping("update")
    public String update(@Valid @ModelAttribute("noticeDto") NoticeDto dto,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/students/noticeUpdate";
        }
        noticeService.updateNotice(dto);
        return "redirect:/notice";
    }
}
