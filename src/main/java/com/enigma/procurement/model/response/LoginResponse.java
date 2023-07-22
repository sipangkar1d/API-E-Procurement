package com.enigma.procurement.model.response;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public class LoginResponse {
    private String username;
    private String token;
    private List<String> roles;

}
