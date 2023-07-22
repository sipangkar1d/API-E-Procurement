package com.enigma.procurement.model.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
@Getter
@Data
public class AuthRequest {
    String email;
    String password;
}
