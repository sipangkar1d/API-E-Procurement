package com.enigma.procurement.config;

import com.enigma.procurement.entity.Admin;
import com.enigma.procurement.entity.Role;
import com.enigma.procurement.entity.UserCredential;
import com.enigma.procurement.entity.constant.ERole;
import com.enigma.procurement.repository.UserCredentialRepository;
import com.enigma.procurement.security.BCryptUtils;
import com.enigma.procurement.service.AdminService;
import com.enigma.procurement.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
class AppInitializer implements CommandLineRunner {
    private final RoleService roleService;
    private final AdminService adminService;
    private final UserCredentialRepository userCredentialRepository;
    private final BCryptUtils cryptUtils;

    @Value("${procurement.email}")
    String email;
    @Value("${procurement.password}")
    String password;
    @Override
    public void run(String... args) {
        Optional<Admin> superAdmin = adminService.findByEmail(email);
        if (superAdmin.isEmpty()){
            Role role = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);
            UserCredential userCredential = UserCredential.builder()
                    .roles(List.of(role))
                    .email(email)
                    .password(cryptUtils.hashPassword(password))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Admin admin = Admin.builder()
                    .name("Super Admin")
                    .email(userCredential.getEmail())
                    .userCredential(userCredential)
                    .build();
            adminService.create(admin);
        }

        log.warn("Super Admin Account has been created on application.properties");
        log.warn("Super Admin Account has been created on application.properties");
        log.warn("Super Admin Account has been created on application.properties");
        log.warn("Super Admin Account has been created on application.properties");
    }
}
