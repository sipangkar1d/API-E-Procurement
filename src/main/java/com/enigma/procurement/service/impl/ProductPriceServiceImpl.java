package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Product;
import com.enigma.procurement.entity.ProductPrice;
import com.enigma.procurement.model.request.ProductPriceRequest;
import com.enigma.procurement.model.response.ProductResponse;
import com.enigma.procurement.repository.ProductPriceRepository;
import com.enigma.procurement.service.ProductPriceService;
import com.enigma.procurement.service.ProductService;
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

    @Override
    public ProductResponse update(ProductPriceRequest request) {
        ProductPrice productPrice = getById(request.getId());
        productPrice.setIsActive(false);

        Product product = productPrice.getProduct();

        ProductPrice newProductPrice = productPriceRepository.saveAndFlush(ProductPrice.builder()
                .price(request.getPrice())
                .product(product)
                .vendor(productPrice.getVendor())
                .isActive(true)
                .build());

        return ProductResponse.builder()
                .price(newProductPrice.getPrice())
                .productName(product.getName())
                .productCode(product.getProductCode())
                .category(product.getCategory().getCategory())
                .vendorName(newProductPrice.getVendor().getName())
                .build();
    }
}
