package com.enigma.procurement.service;

import com.enigma.procurement.entity.ProductPrice;

public interface ProductPriceService {
    ProductPrice create(ProductPrice request);
    ProductPrice getById(String id);
}
