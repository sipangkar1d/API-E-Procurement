package com.enigma.procurement.repository;

import com.enigma.procurement.entity.ProductOnWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOnWarehouseRepository extends JpaRepository<ProductOnWarehouse, String> {
    Optional<ProductOnWarehouse> findByProductCode(String productCode);
}
