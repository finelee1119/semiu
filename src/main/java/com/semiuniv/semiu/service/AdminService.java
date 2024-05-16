package com.semiuniv.semiu.service;

import com.semiuniv.semiu.dto.AdminDto;
import com.semiuniv.semiu.dto.NoticeDto;
import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.entity.Admin;
import com.semiuniv.semiu.repository.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    //등록
    public void insertAdmin(AdminDto dto) {
        Admin admin = AdminDto.toAdminEntity(dto);
        adminRepository.save(admin);
    }

    //조회
    public Page<AdminDto> showAllAdmins(Pageable pageable) {
        return adminRepository.findAll(pageable)
                .map(AdminDto::fromAdminEntity);
    }

    public AdminDto showOneAdmin(Integer id) {
        return adminRepository.findById(id)
                .map(AdminDto::fromAdminEntity)
                .orElse(null);
    }

    //검색
    public Page<AdminDto> searchAdminById(Integer id, Pageable pageable) {
        return adminRepository.findById(id, pageable)
                .map(AdminDto::fromAdminEntity);
    }

    public Page<AdminDto> searchAdminByName(String name, Pageable pageable) {
        return adminRepository.findByNameContaining(name, pageable)
                .map(AdminDto::fromAdminEntity);
    }

    //수정
    public void updateAdmin(AdminDto dto) {
        Admin admin = AdminDto.toAdminEntity(dto);
        adminRepository.save(admin);
    }

    //삭제
    public void deleteAdmin(Integer id) {
        adminRepository.deleteById(id);
    }

    //로그인 후 관리자 정보
    public Optional<Admin> show_admin(Integer loginId) {
        return adminRepository.findById(loginId);
    }

}
