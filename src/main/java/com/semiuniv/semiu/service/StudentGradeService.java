package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.GradeDto;
import com.semiuniv.semiu.dto.StudentGradeDto;
import com.semiuniv.semiu.entity.StudentGrade;
import com.semiuniv.semiu.entity.StudentSubject;
import com.semiuniv.semiu.repository.StudentGradeRepository;
import com.semiuniv.semiu.repository.StudentSubjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class StudentGradeService {
    @Autowired
    StudentGradeRepository studentGradeRepository;

    @Autowired
    StudentSubjectRepository subjectRepository;

    public void SubjectGradeInput(){
        List<StudentSubject> subject = subjectRepository.findAll();
        for(StudentSubject subject1 : subject) {
            StudentGrade grade = new StudentGrade();
            grade.setStudent(subject1.getStudent());
            grade.setSubject(subject1.getSubject());
            studentGradeRepository.save(grade);
        }
    }


    public List<GradeDto> getProfessorGrades(Integer professorId) {
        List<StudentGrade> subjects = studentGradeRepository.findBySubjectProfessorId(professorId);
        log.info(subjects.toString());
        List<GradeDto> gradeDtoList = new ArrayList<>();
        for(StudentGrade subject : subjects){
            GradeDto gradeDto = new GradeDto();
            gradeDto.setAcademicYear(subject.getSubject().getAcademicYear());
            gradeDto.setSemester(subject.getSubject().getSemester());
            gradeDto.setSubjectId(subject.getSubject().getId());
            gradeDto.setSubjectName(subject.getSubject().getName());
            gradeDto.setSubjectType(subject.getSubject().getSubjectType());
            gradeDto.setCredit(subject.getSubject().getCredit());
            gradeDto.setStudentId(subject.getStudent().getId());
            gradeDto.setStudentName(subject.getStudent().getName());
            gradeDto.setGrade(subject.getGrade());
            gradeDtoList.add(gradeDto);
        }
        log.info(gradeDtoList.toString());
        return gradeDtoList;
    }

    public void insertGradeList(List<GradeDto> gradeList){
        for(GradeDto gradeDto : gradeList){
            StudentGrade grade = new StudentGrade();
            grade.getStudent().setId(gradeDto.getStudentId());
            grade.getSubject().setId(gradeDto.getSubjectId());
            grade.setGrade(gradeDto. getGrade());
            studentGradeRepository.save(grade);
        }
    }

    public void insertGrade (StudentGradeDto gradeDto){
        StudentGrade grade = gradeDto.fromStudentGradeDto(gradeDto);
        studentGradeRepository.save(grade);
    }

    public List<StudentGradeDto> findAll() {
        return studentGradeRepository.findAll().stream().map(x -> StudentGradeDto.fromStudentGradeEntity(x)).toList();
    }
}


