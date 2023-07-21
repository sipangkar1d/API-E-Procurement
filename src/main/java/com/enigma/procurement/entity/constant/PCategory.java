package com.enigma.procurement.entity.constant;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum PCategory {
    MAKANAN,
    MINUMAN,
    PAKAIAN;

    public static PCategory get(String value) {
        for (PCategory category : PCategory.values()) {
            if (category.name().equalsIgnoreCase(value))
                return category;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found");
    }
}
