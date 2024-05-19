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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public Page<GradeDto> getProfessorGrades(Integer professorId, Pageable pageable) {
        Page<StudentGrade> studentGradePage = studentGradeRepository.findBySubjectProfessorId(professorId, pageable);
        Page<GradeDto> gradeDtoPage = studentGradePage.map(studentGrade -> {
            GradeDto gradeDto = new GradeDto();
            gradeDto.setNo(studentGrade.getNo());
            gradeDto.setGradeYear(studentGrade.getSubject().getAcademicYear());
            gradeDto.setSemester(studentGrade.getSubject().getSemester());
            gradeDto.setSubjectId(studentGrade.getSubject().getId());
            gradeDto.setSubjectName(studentGrade.getSubject().getName());
            gradeDto.setSubjectType(studentGrade.getSubject().getSubjectType());
            gradeDto.setCredit(studentGrade.getSubject().getCredit());
            gradeDto.setStudentId(studentGrade.getStudent().getId());
            gradeDto.setStudentName(studentGrade.getStudent().getName());
            gradeDto.setDepartment(studentGrade.getStudent().getDepartment().getName());
            gradeDto.setAcademicYear(studentGrade.getStudent().getAcademicYear());
            gradeDto.setGrade(studentGrade.getGrade());
            return gradeDto;
        });
        return gradeDtoPage;
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
    public Page<GradeDto> findAll(Pageable pageable) {
        // 페이징된 데이터 가져오기
        Page<StudentGrade> studentGradePage = studentGradeRepository.findAll(pageable);

        // StudentGrade를 GradeDto로 변환
        Page<GradeDto> gradeDtoPage = studentGradePage.map(studentGrade -> {
            GradeDto gradeDto = new GradeDto();
            gradeDto.setNo(studentGrade.getNo());
            gradeDto.setGradeYear(studentGrade.getSubject().getAcademicYear());
            gradeDto.setSemester(studentGrade.getSubject().getSemester());
            gradeDto.setSubjectId(studentGrade.getSubject().getId());
            gradeDto.setSubjectName(studentGrade.getSubject().getName());
            gradeDto.setSubjectType(studentGrade.getSubject().getSubjectType());
            gradeDto.setCredit(studentGrade.getSubject().getCredit());
            gradeDto.setStudentId(studentGrade.getStudent().getId());
            gradeDto.setStudentName(studentGrade.getStudent().getName());
            gradeDto.setDepartment(studentGrade.getStudent().getDepartment().getName());
            gradeDto.setAcademicYear(studentGrade.getStudent().getAcademicYear());
            gradeDto.setGrade(studentGrade.getGrade());
            return gradeDto;
        });

        return gradeDtoPage;
    }

    // 성적 데이터 삭제 (관리자용)
    public void deleteGrade(int id){
        StudentGrade studentGrade = studentGradeRepository.findByNo(id);
        studentGrade.setGrade(null);
        studentGradeRepository.save(studentGrade);
    }


    public Page<GradeDto> getStudentGrades(Integer studentId, Pageable pageable) {
        Page<StudentGrade> studentGradePage = studentGradeRepository.findByStudentId(studentId, pageable);
        log.info(studentGradePage.toString());
        Page<GradeDto> gradeDtoPage = studentGradePage.map(studentGrade -> {
            GradeDto gradeDto = new GradeDto();
            gradeDto.setNo(studentGrade.getNo());
            gradeDto.setGradeYear(studentGrade.getSubject().getAcademicYear());
            gradeDto.setSemester(studentGrade.getSubject().getSemester());
            gradeDto.setSubjectId(studentGrade.getSubject().getId());
            gradeDto.setSubjectName(studentGrade.getSubject().getName());
            gradeDto.setSubjectType(studentGrade.getSubject().getSubjectType());
            gradeDto.setCredit(studentGrade.getSubject().getCredit());
            gradeDto.setStudentId(studentGrade.getStudent().getId());
            gradeDto.setStudentName(studentGrade.getStudent().getName());
            gradeDto.setDepartment(studentGrade.getStudent().getDepartment().getName());
            gradeDto.setAcademicYear(studentGrade.getStudent().getAcademicYear());
            gradeDto.setGrade(studentGrade.getGrade());
            return gradeDto;
        });
        return gradeDtoPage;
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

    public Page<GradeDto> searchSubjectById(int id, Pageable pageable) {
        return studentGradeRepository.findBySubjectId(id, pageable)
                .map(GradeDto::fromStudentGradeEntity);
    }

    public Page<GradeDto> searchStudentIdAndSubjectId(int id, int keyword, Pageable pageable) {
        return studentGradeRepository.findByStudentIdAndSubjectId(id, keyword, pageable)
                .map(GradeDto::fromStudentGradeEntity);
    }


    public Page<GradeDto> searchStudentById(int id, Pageable pageable) {
        return studentGradeRepository.findByStudentId(id, pageable)
                .map(GradeDto::fromStudentGradeEntity);
    }

    public Page<GradeDto> searchSubjectIdAndStudentId(int id, int keyword, Pageable pageable) {
        return studentGradeRepository.findByProfessorIdAndStudentId(id, keyword, pageable)
                .map(com.semiuniv.semiu.dto.GradeDto::fromStudentGradeEntity);
    }

    public Page<GradeDto> searchSubjectByName(String name, Pageable pageable) {
        return studentGradeRepository.findBySubjectNameContaining(name, pageable)
                .map(GradeDto::fromStudentGradeEntity);
    }

    public Page<GradeDto> searchStudentIdAndSubjectByName(int id, String name, Pageable pageable) {
        return studentGradeRepository.findByStudentIdAndSubjectNameContaining(id, name, pageable)
                .map(GradeDto::fromStudentGradeEntity);
    }


    public void studentSubjectInsert(StudentGradeDto gradeDto){
        StudentGrade studentGrade = new StudentGrade();
        studentGrade.setStudent(gradeDto.getStudent());
        studentGrade.setSubject(gradeDto.getSubject());
        studentGradeRepository.save(studentGrade);
    }

}


