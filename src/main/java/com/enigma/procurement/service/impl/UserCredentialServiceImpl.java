package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.UserCredential;
import com.enigma.procurement.repository.UserCredentialRepository;
import com.enigma.procurement.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {
    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserCredential getByEmail(String email) {
        return userCredentialRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Credential Not Found"));
    }
}
