package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Admin;
import com.enigma.procurement.repository.AdminRepository;
import com.enigma.procurement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public Admin create(Admin request) {
        return adminRepository.saveAndFlush(request);
    }

    @Override
    public Admin update(Admin request) {
        findById(request.getId());
        return adminRepository.save(request);
    }

    @Override
    public Admin findById(String id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin Not Found"));
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findAdminByEmail(email);
    }
}
