package com.semiuniv.semiu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CalendarController {

    @GetMapping("/api/events")
    public List<Map<String, Object>> getEvents() {
        List<Map<String, Object>> events = new ArrayList<>();

        String[][] eventDetails = {
                {"2학기 기말고사 성적마감", "2024-01-02", "2024-01-04"},
                {"복학, 휴학 신청", "2024-01-22", "2024-01-29"},
                {"1학기 수강신청", "2024-02-13", "2024-02-17"},
                {"학위수여식", "2024-02-16", null},
                {"1학기 등록", "2024-02-19", "2024-02-24"},
                {"입학식", "2024-02-26", null},
                {"1학기 개강", "2024-03-04", null},
                {"중간고사", "2024-04-22", "2024-04-27"},
                {"중간고사 성적입력", "2024-04-28", "2024-05-02"},
                {"개교기념일", "2024-05-03", null},
                {"기말고사", "2024-06-17", "2024-06-22"},
                {"기말고사 성적입력", "2024-06-23", "2024-06-27"},
                {"방학 시작", "2024-06-24", null},
                {"복학, 휴학 신청", "2024-07-29", "2024-08-05"},
                {"2학기 수강신청", "2024-08-12", "2024-08-20"},
                {"학위수여식", "2024-08-16", null},
                {"2학기 등록", "2024-08-20", "2024-08-24"},
                {"2학기 개강", "2024-09-02", null},
                {"2학기 중간고사", "2024-10-21", "2024-10-26"},
                {"2학기 중간고사 성적입력", "2024-10-26", "2024-10-30"},
                {"2학기 기말고사", "2024-12-16", "2024-12-21"},
                {"2학기 기말고사 성적입력", "2024-12-22", "2024-12-27"},
                {"방학 시작", "2024-12-23", null},
                {"복학, 휴학 신청", "2025-01-27", "2025-02-03"},
                {"1학기 수강신청", "2025-02-11", "2025-02-15"},
                {"학위수여식", "2025-02-14", null},
                {"1학기 등록", "2025-02-18", "2025-02-22"},
                {"입학식", "2025-02-24", null}
        };

        for (String[] eventDetail : eventDetails) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", eventDetail[0]);
            event.put("start", eventDetail[1]);
            if (eventDetail[2] != null) {
                event.put("end", eventDetail[2]);
            }
            events.add(event);
        }

        return events;
    }
}
