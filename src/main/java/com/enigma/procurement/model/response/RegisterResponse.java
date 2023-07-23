package com.enigma.procurement.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class RegisterResponse {
    private String email;
}
