package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.dto.SubjectDto;
import com.semiuniv.semiu.entity.StudentSubject;
import com.semiuniv.semiu.entity.Subject;
import com.semiuniv.semiu.repository.StudentSubjectRepository;
import com.semiuniv.semiu.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentSubjectRepository studentSubjectRepository;

    //등록
    public void insertSubject(SubjectDto subjectDto){
        Subject subject = subjectDto.fromSubjectDto(subjectDto);
        subjectRepository.save(subject);
    }

    //조회
    public Page<SubjectDto> findSubject(Pageable pageable) {
        return subjectRepository.findAll(pageable)
                .map(SubjectDto::fromSubjectEntity);
    }

    public SubjectDto findSubjectId(int id) {
        SubjectDto subjectDto = subjectRepository.findById(id)
                .map(SubjectDto::fromSubjectEntity)
                .orElse(null);
        return subjectDto;
    }

    //검색
    public Page<SubjectDto> searchSubjectById(Integer id, Pageable pageable) {
        return subjectRepository.findById(id, pageable)
                .map(SubjectDto::fromSubjectEntity);
    }

    public Page<SubjectDto> searchSubjectBySubjectName(String name, Pageable pageable) {
        return subjectRepository.findByNameContaining(name, pageable)
                .map(SubjectDto::fromSubjectEntity);
    }

    //수정
    public void updateSubject(SubjectDto subjectDto) {
        Subject subject = subjectDto.fromSubjectDto(subjectDto);
        subjectRepository.save(subject);
    }

    //삭제
    public void deleteSubject(int id){
        subjectRepository.deleteById(id);
    }

    //수강신청한 과목 정원 증가
    public void updateSubjectTotalStudent(Subject id) {
        id.setTotalStudent(id.getTotalStudent()+1);
    }


    //수강신청 : 과목 정보 + 신청과목 제외 과목 내역
    public Page<Subject> showSubject(Integer id, Pageable pageable) {
        // studentSubject All : id = studentId
        List<StudentSubject> studentSubjects = studentSubjectRepository.findByStudentId(id);
        // id = studentId studentSubject가 없을 경우
        if(studentSubjects.isEmpty()){
            // studentSubject All
            return subjectRepository.findAll(pageable);
        }else{
            // studentSubject All : id = studentId 인 과목 제외 출력 : 쿼리 메소드
            Page<Subject> subjects;
            List<Integer> subjectIds = new ArrayList<>();
            for (StudentSubject subject : studentSubjects){
                Integer subjectId = subject.getSubject().getId();
                subjectIds.add(subjectId);
            }
            subjects = subjectRepository.findByIdNotIn(subjectIds,pageable);
            return subjects;
        }
    }


    //수강신청한 내역 정보
    public List<Subject> showSubjectApplication(Integer id) {
        // studentSubject 전체 : id = student
        List<StudentSubject> studentSubjects = studentSubjectRepository.findByStudentId(id);
//        System.out.println(studentSubjects);
        if(studentSubjects.isEmpty()){
            return null;
        }else{
            List<Subject> subjects = new ArrayList<>();
            List<Integer> subjectIds = new ArrayList<>();
            for (StudentSubject subject : studentSubjects){
                Integer subjectId = subject.getSubject().getId();
//                System.out.println(subjectId);
                subjectIds.add(subjectId);
            }
//            System.out.println(subjectIds);
            subjects = subjectRepository.findByIdIn(subjectIds);
            System.out.println(subjects);
            return subjects;
        }


    }
}
