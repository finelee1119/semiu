package com.semiuniv.semiu.dto;

import com.semiuniv.semiu.entity.Department;
import com.semiuniv.semiu.constant.Gender;
import com.semiuniv.semiu.entity.Student;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Integer id; //학번
    private String name; //성명
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth; //생년월일
    private Gender gender; //성별
    private String address; //주소
    private String phone; //연락처
    private String email;

    private Integer departmentId; // 학과 ID
    private String departmentName; // 학과 이름

    private Integer academicYear; //학년
    private Integer semester; //학기

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entranceDate; //입학일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate graduationDate; //졸업일

    // Entity -> Dto
    public static StudentDto fromStudentEntity(Student student) {
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getBirth(),
                student.getGender(),
                student.getAddress(),
                student.getPhone(),
                student.getEmail(),

                student.getDepartment().getId(),
                student.getDepartment().getName(),

                student.getAcademicYear(),
                student.getSemester(),
                student.getEntranceDate(),
                student.getGraduationDate()
        );
    }

    // DTO -> Entity
    public static Student toStudentEntity(StudentDto dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setBirth(dto.getBirth());
        student.setGender(dto.getGender());
        student.setAddress(dto.getAddress());
        student.setPhone(dto.getPhone());
        student.setEmail(dto.getEmail());

        Department department = new Department();
        department.setId(dto.getDepartmentId());
        student.setDepartment(department);

        student.setAcademicYear(dto.getAcademicYear());
        student.setSemester(dto.getSemester());
        student.setEntranceDate(dto.getEntranceDate());
        student.setGraduationDate(dto.getGraduationDate());
        return student;
    }
}
