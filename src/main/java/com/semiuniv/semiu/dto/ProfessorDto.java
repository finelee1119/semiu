package com.semiuniv.semiu.dto;

import com.semiuniv.semiu.constant.Gender;
import com.semiuniv.semiu.entity.Department;
import com.semiuniv.semiu.entity.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDto {

    private Integer id; //교번

    private String name; //성명

    private LocalDate birth; //생년월일

    private Gender gender; //성별

    private String address; //주소

    private String phone; //연락처

    //    private Department department; // 학과
    private Integer departmentId; // 학과 ID
    private String departmentName; // 학과 이름

    private LocalDate hireDate; //입사일

    private LocalDate terminationDate; //퇴사일

    // Entity -> Dto
    public static ProfessorDto fromProfessorEntity(Professor professor) {
        return new ProfessorDto(
                professor.getId(),
                professor.getName(),
                professor.getBirth(),
                professor.getGender(),
                professor.getAddress(),
                professor.getPhone(),

                professor.getDepartment().getId(),
                professor.getDepartment().getName(),

                professor.getHireDate(),
                professor.getTerminationDate()
        );
    }

    // DTO -> Entity
    public static Professor toProfessorEntity(ProfessorDto dto) {
        Professor professor = new Professor();
        professor.setId(dto.getId());
        professor.setName(dto.getName());
        professor.setBirth(dto.getBirth());
        professor.setGender(dto.getGender());
        professor.setAddress(dto.getAddress());
        professor.setPhone(dto.getPhone());

        Department department = new Department();
        department.setId(dto.getDepartmentId());
        professor.setDepartment(department);

        professor.setHireDate(dto.getHireDate());
        professor.setTerminationDate(dto.getTerminationDate());
        return professor;
    }
}
