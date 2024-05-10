package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.SubjectDto;
import com.semiuniv.semiu.entity.Subject;
import com.semiuniv.semiu.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<SubjectDto> findSubject() {
        List<SubjectDto> subjectDtoList = subjectRepository.findAll()
                .stream().map(x -> SubjectDto.fromSubjectEntity(x)).collect(Collectors.toList());
        return subjectDtoList;
    }
    public void insertSubject(SubjectDto subjectDto){
        Subject subject = subjectDto.fromSubjectDto(subjectDto);
        subjectRepository.save(subject);
    }

    public SubjectDto findSubjectId(int id) {
        SubjectDto subjectDto = subjectRepository.findById(id).map(x ->SubjectDto.fromSubjectEntity(x)).orElse(null);
        return subjectDto;
    }

    public void updateSubject(SubjectDto subjectDto) {
        Subject subject = subjectDto.fromSubjectDto(subjectDto);
        subjectRepository.save(subject);
    }

    public void deleteSubject(int id){
        subjectRepository.deleteById(id);
    }

    //수강신청 : 과목 정보 +
    public List<Subject> showSubject() {
        return subjectRepository.findAll();
    }
}
