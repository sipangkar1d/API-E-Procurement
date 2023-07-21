package com.enigma.procurement.service;

import com.enigma.procurement.entity.Category;
import com.enigma.procurement.entity.constant.PCategory;
import com.enigma.procurement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface CategoryService {
    Category getOrSave(PCategory category);
}
