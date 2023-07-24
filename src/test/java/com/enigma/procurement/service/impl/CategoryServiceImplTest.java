package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Category;
import com.enigma.procurement.repository.CategoryRepository;
import com.enigma.procurement.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void shouldReturnCategoryWhenCategoryFound() {
        String name = "ATK";
        Category category = Category.builder().category(name).build();

        when(categoryRepository.findByCategory(name)).thenReturn(Optional.ofNullable(category));
        Optional<Category> actual = categoryRepository.findByCategory(name);

        Assertions.assertNotNull(actual);
    }
    @Test
    void shouldReturnCategoryWhenCategoryNotFound() {
        String name = "ATK";
        Category category = Category.builder().category(name).build();
        when(categoryRepository.findByCategory(name)).thenReturn(Optional.empty());
        when(categoryRepository.saveAndFlush(category)).thenReturn(category);
        Category actual = categoryService.getOrSave(name);

        assertEquals(category, actual);
    }
}