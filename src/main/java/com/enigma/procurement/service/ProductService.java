package com.enigma.procurement.service;

import com.enigma.procurement.entity.Product;
import com.enigma.procurement.model.request.ProductRequest;
import com.enigma.procurement.model.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    Page<ProductResponse> getAll( Integer page, Integer size);
    Product findById(String id);
}
