package com.semiuniv.semiu.controller;

import com.semiuniv.semiu.dto.StudentGradeDto;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.StudentGrade;
import com.semiuniv.semiu.entity.StudentSubject;
import com.semiuniv.semiu.entity.Subject;
import com.semiuniv.semiu.service.StudentGradeService;
import com.semiuniv.semiu.service.StudentService;
import com.semiuniv.semiu.service.StudentSubjectService;
import com.semiuniv.semiu.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/semi")
@RequiredArgsConstructor
public class ApplicationController {
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final StudentSubjectService studentSubjectService;
    private final StudentGradeService studentGradeService;

    //수강 신청 페이지 : 로그인 전
    @GetMapping("application")
    public String lectureApplicationView(Model model){
        return "application/lectureApplication";
    }

    //수강 신청 페이지 : username로 이동 (조건 : 로그인 후)
    @GetMapping("application/{id}")
    public String lectureApplicationLoginView(@PathVariable("id") Integer id,
                                              Model model,
                                              Pageable pageable){
        //로그인한 학생 정보 : student
        Optional<Student> studentInfo = studentService.findById(id);
        Student studentLogin = studentInfo.get();
        model.addAttribute("studentLogin", studentLogin);

        //과목 목록 : subject : 조건-수강신청 완료한 과목 제외
        Page<Subject> subject = subjectService.showSubject(id,pageable);
        model.addAttribute("subject", subject);

        //학생이 신청한 과목 내역 new : studentSubject
        model.addAttribute("studentSubject", new StudentSubject());
        return "application/lectureApplication";
    }

    //수강 신청 페이지 : 수강 신청 내역 전송 ( 조건 : 로그인 한 학생 정보 데이터에 추가)
    @PostMapping("application/insert")
    public String test(@ModelAttribute("subject") Subject subject){
        //신청 과목 목록
        System.out.printf(subject.toString());

        return "application/lectureApplication";
    }

    //수강 신청 페이지 : 수강 신청 내역 전송 ( 조건 : 로그인 한 학생 정보 데이터에 추가)
    @PostMapping("application/insert/{id}")
    public String lectureApplicationInsert(@PathVariable("id") Student studentId,
                                           @RequestParam("checkedIds") Subject[] checkedIds,
                                           @ModelAttribute("studentSubject") StudentSubject studentSubject) {

        //studentSubject : subject : 해당 학생이 신청한 과목 내역 하나씩 추가
        for (Subject id : checkedIds){
//            System.out.println(checkedIds.toString());

            //studentSubject : student : 해당 학생 id 추가
            studentSubject = new StudentSubject();
            studentSubject.setStudent(studentId);
            studentSubject.setSubject(id);
            studentSubjectService.insertApplication(studentSubject);
            //StudentGrade 데이터 추가
            StudentGradeDto gradedto = new StudentGradeDto();
            gradedto.setStudent(studentId);
            gradedto.setSubject(id);
            studentGradeService.studentSubjectInsert(gradedto);
            
            //subject : 정원 변경 +증가
            subjectService.updateSubjectTotalStudent(id);
        }

        return "redirect:/semi/application/{id}";
    }

    //수강 조회 페이지 : username로 이동 (조건 : 로그인 후)
    @GetMapping("application/show/{id}")
    public String lectureApplicationShow(@PathVariable("id") Integer id,
                                         Model model){
        //로그인한 학생 정보 : student
        Optional<Student> studentInfo = studentService.findById(id);
        Student studentLogin = studentInfo.get();
        model.addAttribute("studentLogin", studentLogin);

        //과목 목록 : subject : 조건-수강신청한 과목 내역
        List<Subject> subjectApplication = subjectService.showSubjectApplication(id);
        model.addAttribute("subjectApplication", subjectApplication);

        //시간표 : 날짜 분리
        Map<String, List<Subject>> subjectMap = new LinkedHashMap<>();
        List<String> daysOfWeek = Arrays.asList("월", "화", "수", "목", "금");

        for (String day : daysOfWeek) {
            List<Subject> subjectsForDay = subjectApplication.stream()
                    .filter(sub -> sub.getDayOfWeek().equals(day))
                    .collect(Collectors.toList());
            subjectMap.put(day, subjectsForDay);
        }
        System.out.println("-------------------------------"+subjectMap.toString());
        model.addAttribute("subjectMap", subjectMap);

        return "application/showLectureApplication";
    }
}
