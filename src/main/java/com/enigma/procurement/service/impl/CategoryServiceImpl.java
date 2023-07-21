package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Category;
import com.enigma.procurement.entity.constant.PCategory;
import com.enigma.procurement.repository.CategoryRepository;
import com.enigma.procurement.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category getOrSave(PCategory category) {
        return categoryRepository.findByCategory(category)
                .orElseGet(() -> categoryRepository.save(Category.builder().category(category).build()));
    }
}
