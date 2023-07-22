package com.enigma.procurement.service;

import com.enigma.procurement.entity.Admin;

import java.util.Optional;

public interface AdminService {
    Admin create(Admin request);
    Admin update(Admin request);
    Admin findById(String id);
    Optional<Admin> findByEmail(String email);

}
