package com.enigma.procurement.entity.constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum ERole {
    ROLE_SUPER_ADMIN,
    ROLE_ADMIN;

    public static ERole get(String val) {
        for (ERole eRole : ERole.values()) {
            if (eRole.name().equalsIgnoreCase(val))
                return eRole;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "role not found");
    }
}


