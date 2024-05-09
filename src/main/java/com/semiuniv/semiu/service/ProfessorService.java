package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    //등록
    public void insertProfessor(ProfessorDto dto) {
        Professor professor = ProfessorDto.toProfessorEntity(dto);
        professorRepository.save(professor);
    }

    //조회
    public List<ProfessorDto> showAllProfessors() {
        List<ProfessorDto> ProfessorDtoList = new ArrayList<>();
        return professorRepository.findAll()
                .stream()
                .map(ProfessorDto::fromProfessorEntity)
                .toList();
    }

    public ProfessorDto showOneProfessor(Integer id) {
        return professorRepository.findById(id)
                .map(ProfessorDto::fromProfessorEntity)
                .orElse(null);
    }

    //수정
    public void updateProfessor(ProfessorDto dto) {
        Professor professor = ProfessorDto.toProfessorEntity(dto);
        professorRepository.save(professor);
    }

    //삭제
    public void deleteProfessor(Integer id) {
        professorRepository.deleteById(id);
    }
}
