package com.enigma.procurement.model.response;

import lombok.Builder;

@Builder(toBuilder = true)
public class RegisterResponse {
    private String email;
}
