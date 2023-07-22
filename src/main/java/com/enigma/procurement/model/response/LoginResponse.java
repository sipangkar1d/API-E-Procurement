package com.enigma.procurement.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class LoginResponse {
    private String username;
    private String token;
    private List<String> roles;

}
