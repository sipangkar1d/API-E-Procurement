package com.enigma.procurement.service;

import com.enigma.procurement.entity.ProductPrice;
import com.enigma.procurement.model.request.ProductPriceRequest;
import com.enigma.procurement.model.response.ProductResponse;

public interface ProductPriceService {
    ProductPrice create(ProductPrice request);
    ProductPrice getById(String id);
    ProductResponse update(ProductPriceRequest request);
}
