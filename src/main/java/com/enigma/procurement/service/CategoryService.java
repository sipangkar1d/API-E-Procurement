package com.enigma.procurement.service;

import com.enigma.procurement.entity.Category;

public interface CategoryService {
    Category getOrSave(String category);
}
