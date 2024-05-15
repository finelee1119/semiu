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


    // 강사 성적 조회/입력/수정 (강사 아이디로 데이터 조회)
    public List<GradeDto> getProfessorGrades(Integer professorId) {
        List<StudentGrade> subjects = studentGradeRepository.findBySubjectProfessorId(professorId).stream().collect(Collectors.toList());
        List<GradeDto> gradeDtoList = new ArrayList<>();
        for (StudentGrade subject : subjects) {
            GradeDto gradeDto = new GradeDto();
            gradeDto.setGradeYear(subject.getSubject().getAcademicYear());
            gradeDto.setSemester(subject.getSubject().getSemester());
            gradeDto.setSubjectId(subject.getSubject().getId());
            gradeDto.setSubjectName(subject.getSubject().getName());
            gradeDto.setSubjectType(subject.getSubject().getSubjectType());
            gradeDto.setCredit(subject.getSubject().getCredit());
            gradeDto.setStudentId(subject.getStudent().getId());
            gradeDto.setStudentName(subject.getStudent().getName());
            gradeDto.setDepartment(subject.getStudent().getDepartment().getName());
            gradeDto.setAcademicYear(subject.getStudent().getAcademicYear());
            gradeDto.setGrade(subject.getGrade());
            gradeDtoList.add(gradeDto);
        }
        return gradeDtoList;
    }

    // 강사 성적 등록
    public void insertGrade(StudentGradeDto gradeDto) {
        List<StudentGrade> studentGrade = studentGradeRepository.findAll();
        for (StudentGrade grade : studentGrade) {
            if (grade.getStudent().getId().equals(gradeDto.getStudent().getId())) {
                gradeDto.setStudent(grade.getStudent());
            }
            if (grade.getSubject().getId().equals(gradeDto.getSubject().getId())) {
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

    // 성적 전체 조회 (관리자용)
    public List<GradeDto> findAll() {
        List<StudentGrade> grades = studentGradeRepository.findAll().stream().collect(Collectors.toList());
        List<GradeDto> gradeDtoList = new ArrayList<>();
        for (StudentGrade grade : grades) {
            GradeDto gradeDto = new GradeDto();
            gradeDto.setNo(grade.getNo());
            gradeDto.setGradeYear(grade.getSubject().getAcademicYear());
            gradeDto.setSemester(grade.getSubject().getSemester());
            gradeDto.setSubjectId(grade.getSubject().getId());
            gradeDto.setSubjectName(grade.getSubject().getName());
            gradeDto.setSubjectType(grade.getSubject().getSubjectType());
            gradeDto.setCredit(grade.getSubject().getCredit());
            gradeDto.setStudentId(grade.getStudent().getId());
            gradeDto.setStudentName(grade.getStudent().getName());
            gradeDto.setDepartment(grade.getStudent().getDepartment().getName());
            gradeDto.setAcademicYear(grade.getStudent().getAcademicYear());
            gradeDto.setGrade(grade.getGrade());
            gradeDtoList.add(gradeDto);
        }
        return gradeDtoList;
    }

    // 성적 데이터 삭제 (관리자용)
    public void deleteGrade(int id){
        studentGradeRepository.deleteById(id);
    }


    public List<GradeDto> getStudentGrades(Integer StudentId) {
        List<StudentGrade> grade = studentGradeRepository.findByStudentId(StudentId).stream().collect(Collectors.toList());
        List<GradeDto> gradeDtoList = new ArrayList<>();
        for (StudentGrade subject : grade) {
            GradeDto gradeDto = new GradeDto();
            gradeDto.setGradeYear(subject.getSubject().getAcademicYear());
            gradeDto.setSemester(subject.getSubject().getSemester());
            gradeDto.setSubjectId(subject.getSubject().getId());
            gradeDto.setSubjectName(subject.getSubject().getName());
            gradeDto.setSubjectType(subject.getSubject().getSubjectType());
            gradeDto.setCredit(subject.getSubject().getCredit());
            gradeDto.setStudentId(subject.getStudent().getId());
            gradeDto.setStudentName(subject.getStudent().getName());
            gradeDto.setDepartment(subject.getStudent().getDepartment().getName());
            gradeDto.setAcademicYear(subject.getStudent().getAcademicYear());
            gradeDto.setGrade(subject.getGrade());
            gradeDtoList.add(gradeDto);
        }
        return gradeDtoList;
    }


    // FK 존재여부 확인 (삭제 알럿 검증용)
    public StudentGradeDto findByStudentIdAndSubjectId(@Param("studentId") Integer studentId, @Param("subjectId") Integer subjectId) {
        StudentGrade grade = studentGradeRepository.findByStudentAndSubject(studentId, subjectId);
        StudentGradeDto gradeDto = StudentGradeDto.fromStudentGradeEntity(grade);
        return gradeDto;
    }

    public boolean findBySubjectId(Integer id) {
        SubjectDto dto = subjectService.findSubjectId(id);
        Subject subject = dto.fromSubjectDto(dto);
        StudentGrade grade = studentGradeRepository.findBySubjectId(subject.getId());
        boolean empty = false;
        if (grade == null) {
            empty = true;
        } else {
            empty = false;
        }
        return empty;
    }
}


