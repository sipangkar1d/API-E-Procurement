package com.enigma.procurement.repository;

import com.enigma.procurement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends JpaRepository<Product, String> , JpaSpecificationExecutor<Product> {
}
