package com.semiuniv.semiu.dto;

import com.semiuniv.semiu.constant.SubjectType;
import com.semiuniv.semiu.entity.Classroom;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

    private Integer id;
    private String name;
    private Professor professor;
    private Classroom classroom;
    private SubjectType subjectType;
    private Integer credit; //이수학점(전공 3학점, 교양1~2학점 등)
    private Integer academicYear;
    private Integer semester;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxStudent; //수강 정원
    private Integer totalStudent; //현재 학생 수

    public static SubjectDto fromSubjectEntity(Subject subject){
        return new SubjectDto(
                subject.getId(), subject.getName(), subject.getProfessor(), subject.getClassroom(),
                subject.getSubjectType(), subject.getCredit(), subject.getAcademicYear(),
                subject.getSemester(), subject.getDayOfWeek(), subject.getStartTime(),
                subject.getEndTime(), subject.getMaxStudent(), subject.getTotalStudent());
    }
    public Subject fromSubjectDto(SubjectDto dto){
        Subject subject = new Subject();
        subject.setId(dto.getId());
        subject.setName(dto.getName());
        subject.setProfessor(dto.getProfessor());
        subject.setClassroom(dto.getClassroom());
        subject.setSubjectType(dto.getSubjectType());
        subject.setCredit(dto.getCredit());
        subject.setAcademicYear(dto.getAcademicYear());
        subject.setSemester(dto.getSemester());
        subject.setDayOfWeek(dto.getDayOfWeek());
        subject.setStartTime(dto.getStartTime());
        subject.setEndTime(dto.getEndTime());
        subject.setMaxStudent(dto.getMaxStudent());
        subject.setTotalStudent(dto.getTotalStudent());
        return subject;
    }
}
