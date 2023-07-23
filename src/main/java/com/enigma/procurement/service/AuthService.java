package com.enigma.procurement.service;

import com.enigma.procurement.model.request.AuthRequest;
import com.enigma.procurement.model.request.AuthVendorRequest;
import com.enigma.procurement.model.response.LoginResponse;
import com.enigma.procurement.model.response.RegisterResponse;
import com.enigma.procurement.model.response.RegisterVendorResponse;

public interface AuthService {
    LoginResponse login(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    RegisterVendorResponse registerVendor(AuthVendorRequest request);
}
