package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Category;
import com.enigma.procurement.entity.Product;
import com.enigma.procurement.entity.ProductPrice;
import com.enigma.procurement.entity.Vendor;
import com.enigma.procurement.model.request.ProductRequest;
import com.enigma.procurement.model.response.ProductResponse;
import com.enigma.procurement.repository.ProductRepository;
import com.enigma.procurement.service.CategoryService;
import com.enigma.procurement.service.ProductPriceService;
import com.enigma.procurement.service.ProductService;
import com.enigma.procurement.service.VendorService;
import com.enigma.procurement.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final VendorService vendorService;
    private final ProductPriceService productPriceService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse create(ProductRequest request) {
        validationUtil.validate(request);

        Vendor vendor = vendorService.getById(request.getVendorId());
        Category category = categoryService.getOrSave(request.getCategory());
        String generateProductCode = generateProductCode();

        Product product = Product.builder()
                .name(request.getProductName())
                .category(category)
                .productCode(generateProductCode)
                .isActive(true)
                .build();
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(request.getPrice())
                .product(product)
                .vendor(vendor)
                .isActive(true)
                .build();
        productPriceService.create(productPrice);

        product.setProductPrices(List.of(productPrice));

        return buildProductResponse(vendor, product, productPrice);
    }

    @Override
    public Page<ProductResponse> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products = productRepository.findAll(pageable);

        List<ProductResponse> productResponses = new ArrayList<>();

        for (Product product : products.getContent()) {
            Optional<ProductPrice> productPriceIsActive = product.getProductPrices()
                    .stream().filter(ProductPrice::getIsActive).findFirst();

            if (productPriceIsActive.isEmpty()) continue;

            Vendor vendor = productPriceIsActive.get().getVendor();

            productResponses.add(buildProductResponse(vendor, product, productPriceIsActive.get()));
        }

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    @Override
    public Product findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));
        if (product.getIsActive().equals(false))
            throw new ResponseStatusException(HttpStatus.GONE, "Product Was Not Active");

        return product;
    }

    private Product findByCode(String code) {
        return productRepository.findByProductCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

    }

    private String generateProductCode() {
        long productSize = productRepository.findAll().size();
        return String.format("KB%04d", productSize + 1);
    }

    private static ProductResponse buildProductResponse(Vendor vendor, Product product, ProductPrice productPrice) {
        return ProductResponse.builder()
                .productName(product.getName())
                .productCode(product.getProductCode())
                .category(product.getCategory().getCategory())
                .price(productPrice.getPrice())
                .vendorName(vendor.getName())
                .build();
    }
}
