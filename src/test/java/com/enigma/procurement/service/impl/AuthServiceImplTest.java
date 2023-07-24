package com.enigma.procurement.service.impl;

import com.enigma.procurement.model.request.AuthRequest;
import com.enigma.procurement.repository.UserCredentialRepository;
import com.enigma.procurement.security.BCryptUtils;
import com.enigma.procurement.security.JwtUtils;
import com.enigma.procurement.service.*;
import com.enigma.procurement.util.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthService authService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserCredentialRepository userCredentialRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private RoleService roleService;
    @Mock
    private BCryptUtils bCryptUtils;
    @Mock
    private AdminService adminService;
    @Mock
    private ValidationUtil validationUtil;
    @Mock
    private VendorService vendorService;
    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(
                authenticationManager,
                jwtUtils,
                roleService,
                bCryptUtils,
                userCredentialRepository,
                adminService,
                validationUtil,
                vendorService,
                productService
        );
    }

    @Test
    void login() {
    }

    @Test
    void registerAdmin() {
    }

    @Test
    void registerVendor() {
    }
}