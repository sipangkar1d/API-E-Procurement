package com.enigma.procurement.util;

import com.enigma.procurement.entity.UserCredential;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String>{
    @Override
    public Optional<String> getCurrentAuditor() {
        UserCredential currentUser = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(currentUser.getEmail());
    }
}
