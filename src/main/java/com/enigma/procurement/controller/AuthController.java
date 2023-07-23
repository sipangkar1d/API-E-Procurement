package com.enigma.procurement.controller;

import com.enigma.procurement.model.request.AuthRequest;
import com.enigma.procurement.model.request.AuthVendorRequest;
import com.enigma.procurement.model.response.CommonResponse;
import com.enigma.procurement.model.response.LoginResponse;
import com.enigma.procurement.model.response.RegisterResponse;
import com.enigma.procurement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        LoginResponse loginResponse = authService.login(request);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Success")
                .data(loginResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(commonResponse);
    }

    @PostMapping("/register-admin")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        RegisterResponse response = authService.registerAdmin(request);

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully Created Admin")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/register-vendor")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<?> registerVendor(@RequestBody AuthVendorRequest request) {

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully Created Vendor")
                .data(authService.registerVendor(request))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

}
