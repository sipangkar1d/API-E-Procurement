package com.enigma.procurement.controller;

import com.enigma.procurement.model.request.AuthRequest;
import com.enigma.procurement.model.response.CommonResponse;
import com.enigma.procurement.model.response.LoginResponse;
import com.enigma.procurement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Success")
                .data(authService.login(request))
                .build();
        System.out.println("Success");
        return ResponseEntity.status(HttpStatus.OK)
                .body(commonResponse);
    }
}
