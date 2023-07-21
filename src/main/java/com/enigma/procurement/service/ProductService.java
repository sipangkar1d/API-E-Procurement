package com.enigma.procurement.service;

import com.enigma.procurement.entity.Product;
import com.enigma.procurement.model.request.ProductRequest;
import com.enigma.procurement.model.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    Page<ProductResponse> getAll(String name, Long maxPrice, Integer page, Integer size);
    ProductResponse update(ProductRequest request);
    void delete(String id);

    Product findById(String id);
}
