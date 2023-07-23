package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.ProductOnWarehouse;
import com.enigma.procurement.model.request.ProductOnWarehouseRequest;
import com.enigma.procurement.repository.ProductOnWarehouseRepository;
import com.enigma.procurement.service.ProductOnWarehouseService;
import com.enigma.procurement.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductOnWarehouseServiceImpl implements ProductOnWarehouseService {
    private final ProductOnWarehouseRepository productOnWarehouseRepository;
    private final ValidationUtil validationUtil;

    @Override
    public Page<ProductOnWarehouse> getAll(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductOnWarehouse> productsOnWarehouse = productOnWarehouseRepository.findAll(pageable);

        return new PageImpl<>(productsOnWarehouse.getContent(), pageable, productsOnWarehouse.getTotalElements());
    }

    @Override
    public ProductOnWarehouse createOrUpdate(ProductOnWarehouseRequest request) {
        Optional<ProductOnWarehouse> product = productOnWarehouseRepository.findByProductCode(request.getProductCode());

        if (product.isPresent()) {
            product.get().setQuantity(product.get().getQuantity() + request.getQuantity());
            return productOnWarehouseRepository.save(product.get());
        }

        return productOnWarehouseRepository.saveAndFlush(
                ProductOnWarehouse.builder()
                        .name(request.getName())
                        .quantity(request.getQuantity())
                        .productCode(request.getProductCode())
                        .build()
        );

    }
}
