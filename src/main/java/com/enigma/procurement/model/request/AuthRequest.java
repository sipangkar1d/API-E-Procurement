package com.enigma.procurement.model.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Data
public class AuthRequest {
    @NotBlank(message = "Required email")
    String email;

    @NotBlank(message = "Required password")
    String password;
}
