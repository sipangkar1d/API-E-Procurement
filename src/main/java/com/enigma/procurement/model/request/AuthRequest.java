package com.enigma.procurement.model.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
@Getter
public class AuthRequest {
    @NotBlank(message = "email is required")
    String email;
    @NotBlank(message = "password is required")
    String password;
}
