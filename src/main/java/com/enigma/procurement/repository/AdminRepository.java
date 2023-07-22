package com.enigma.procurement.repository;

import com.enigma.procurement.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findAdminByEmail(String email);
}
