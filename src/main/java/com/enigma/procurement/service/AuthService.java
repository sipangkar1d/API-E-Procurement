package com.enigma.procurement.service;

import com.enigma.procurement.model.request.AuthRequest;
import com.enigma.procurement.model.response.LoginResponse;

public interface AuthService {
    LoginResponse login(AuthRequest request);
}
