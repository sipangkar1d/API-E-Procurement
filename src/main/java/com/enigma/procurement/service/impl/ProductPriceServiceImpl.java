package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.ProductPrice;
import com.enigma.procurement.repository.ProductPriceRepository;
import com.enigma.procurement.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {
    private final ProductPriceRepository productPriceRepository;

    @Override
    public ProductPrice create(ProductPrice request) {
        return productPriceRepository.saveAndFlush(request);
    }

    @Override
    public ProductPrice getById(String id) {
        ProductPrice productPrice = productPriceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Price Not Found"));
        if (productPrice.getIsActive().equals(false))
            throw new ResponseStatusException(HttpStatus.GONE, "Product Price Was Not Active");
        return productPrice;
    }
}
