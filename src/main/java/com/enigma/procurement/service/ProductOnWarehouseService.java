package com.enigma.procurement.service;

import com.enigma.procurement.entity.ProductOnWarehouse;
import com.enigma.procurement.model.request.ProductOnWarehouseRequest;
import org.springframework.data.domain.Page;

public interface ProductOnWarehouseService {
    Page<ProductOnWarehouse> getAll(Integer size, Integer page);
    ProductOnWarehouse createOrUpdate(ProductOnWarehouseRequest request);
}
