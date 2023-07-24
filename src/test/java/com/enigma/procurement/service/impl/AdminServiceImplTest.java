package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Admin;
import com.enigma.procurement.entity.UserCredential;
import com.enigma.procurement.repository.AdminRepository;
import com.enigma.procurement.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        adminService = new AdminServiceImpl(
                adminRepository
        );
    }

    @Test
    void shouldReturnAdminWhenCreateAdmin() {
        Admin admin = new Admin();
        admin.setEmail("admin@admin.com");
        admin.setName("admin");
        admin.setUserCredential(new UserCredential());

        when(adminRepository.saveAndFlush(admin)).thenReturn(admin);
        Admin actual = adminRepository.saveAndFlush(admin);

        Assertions.assertNotNull(actual);
    }

    @Test
    void update() {
        Admin admin = Admin.builder().build();

        when(adminRepository.save(admin)).thenReturn(admin);
        Admin actual = adminRepository.save(admin);

        Assertions.assertEquals(admin, actual);
    }

    @Test
    void shouldReturnAdminWhenFindAdminById() {
        String id = "1";

        when(adminRepository.findById(id))
                .thenReturn(Optional.ofNullable(Admin.builder().id("1").build()));
        Optional<Admin> actual = adminRepository.findById(id);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.get().getId(), id);
    }

    @Test
    void shouldThrowNotFoundWhenFindAdminById() {
        String id = "1";

        when(adminRepository.findById(id))
                .thenThrow(ResponseStatusException.class);

        Assertions.assertThrows(ResponseStatusException.class, () -> adminRepository.findById(id));
    }

    @Test
    void shouldReturnAdminWhenFindAdminByEmail() {
        String email = "admin@example.com";
        Admin admin = Admin.builder().email(email).build();

        when(adminRepository.findAdminByEmail(email)).thenReturn(Optional.of(admin));
        Optional<Admin> actual = adminService.findByEmail(email);

        Assertions.assertEquals(admin, actual.get());
    }

    @Test
    void shouldReturnNullWhenFindAdminByEmail() {
        String email = "admin@example.com";

        when(adminRepository.findAdminByEmail(email)).thenReturn(null);
        Optional<Admin> actual = adminService.findByEmail(email);

        Assertions.assertNull(actual);
    }
}