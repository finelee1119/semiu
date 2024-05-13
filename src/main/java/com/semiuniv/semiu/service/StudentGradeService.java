package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.GradeDto;
import com.semiuniv.semiu.dto.StudentGradeDto;
import com.semiuniv.semiu.dto.SubjectDto;
import com.semiuniv.semiu.entity.StudentGrade;
import com.semiuniv.semiu.entity.StudentSubject;
import com.semiuniv.semiu.entity.Subject;
import com.semiuniv.semiu.repository.StudentGradeRepository;
import com.semiuniv.semiu.repository.StudentSubjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentGradeService {
    @Autowired
    StudentGradeRepository studentGradeRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;
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
        List<StudentGrade> subjects = studentGradeRepository.findBySubjectProfessorId(professorId).stream().collect(Collectors.toList());
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
        List<StudentGrade> studentGrade = studentGradeRepository.findAll();
        for(StudentGrade grade : studentGrade){
            if(grade.getStudent().getId().equals(gradeDto.getStudent().getId())){
                gradeDto.setStudent(grade.getStudent());
            }
            if(grade.getSubject().getId().equals(gradeDto.getSubject().getId())){
                gradeDto.setSubject(grade.getSubject());
            }
        }
        log.info(gradeDto.toString());
        StudentGrade existingStudentGrade = studentGradeRepository.findByStudentAndSubject(gradeDto.getStudent().getId(), gradeDto.getSubject().getId());

        if (existingStudentGrade != null) {
            // 이미 있는 데이터인 경우에는 성적을 업데이트
            existingStudentGrade.setGrade(gradeDto.getGrade());
            studentGradeRepository.save(existingStudentGrade);
        }
    }

    public List<StudentGradeDto> findAll() {
        return studentGradeRepository.findAll()
                .stream()
                .map(x -> StudentGradeDto.fromStudentGradeEntity(x))
                .collect(Collectors.toList());
    }

    public StudentGradeDto findByStudentIdAndSubjectId(@Param("studentId")Integer studentId, @Param("subjectId")Integer subjectId){
        StudentGrade grade = studentGradeRepository.findByStudentAndSubject(studentId, subjectId);
        StudentGradeDto gradeDto = StudentGradeDto.fromStudentGradeEntity(grade);
        return gradeDto;
    }

    public boolean findBySubjectId(Integer id){
        SubjectDto dto = subjectService.findSubjectId(id);
        Subject subject = dto.fromSubjectDto(dto);
        StudentGrade grade = studentGradeRepository.findBySubjectId(subject.getId());
        boolean empty = false;
        if(grade == null) {
            empty = true;
        } else {
            empty = false;
        }
        return empty;
    }

//    public StudentGradeDto findByStudentId(Integer subjectId){
//        StudentGrade grade = studentGradeRepository.findBySubjectId(subjectId);
//        return grade;
//    }
}


