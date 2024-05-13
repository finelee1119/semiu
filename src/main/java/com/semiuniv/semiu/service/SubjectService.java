package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.dto.SubjectDto;
import com.semiuniv.semiu.entity.Subject;
import com.semiuniv.semiu.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

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

    //수강신청 : 과목 정보 +
    public List<Subject> showSubject() {
        return subjectRepository.findAll();
    }
}
