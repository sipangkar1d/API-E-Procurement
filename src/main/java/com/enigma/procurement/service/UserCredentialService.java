package com.enigma.procurement.service;

import com.enigma.procurement.entity.UserCredential;

public interface UserCredentialService {
    UserCredential getByEmail(String email);
}
