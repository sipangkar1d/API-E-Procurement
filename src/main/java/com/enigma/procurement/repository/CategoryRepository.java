package com.enigma.procurement.repository;

import com.enigma.procurement.entity.Category;
import com.enigma.procurement.entity.constant.PCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByCategory(PCategory category);

}
