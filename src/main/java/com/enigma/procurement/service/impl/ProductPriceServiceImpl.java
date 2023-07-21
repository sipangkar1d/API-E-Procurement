package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.ProductPrice;
import com.enigma.procurement.repository.ProductPriceRepository;
import com.enigma.procurement.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {
    private final ProductPriceRepository productPriceRepository;
    @Override
    public ProductPrice create(ProductPrice request) {
        return productPriceRepository.saveAndFlush(request);
    }
}
