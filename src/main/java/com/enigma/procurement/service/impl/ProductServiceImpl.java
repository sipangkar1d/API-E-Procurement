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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductPriceService productPriceService;
    private final VendorService vendorService;


    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse create(ProductRequest request) {
        Vendor vendor = vendorService.getById(request.getVendorId());
        Category category = categoryService.getOrSave(request.getCategory());
        System.out.println(category);
        Product product = Product.builder().build();
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(request.getPrice())
                .stock(request.getStock())
                .product(product)
                .vendor(vendor)
                .isActive(true)
                .build();
        productPriceService.create(productPrice);

        product.setName(request.getProductName());
        product.setCategory(category);
        product.setProductCode(generateProductCode());
        product.setProductPrices(List.of(productPrice));
        product.setIsActive(true);

        return buildProductResponse(vendor, category, product, productPrice);
    }


    @Override
    public Page<ProductResponse> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products = productRepository.findAll(pageable);
        List<ProductResponse> productResponses = new ArrayList<>();

        for (Product product : products.getContent()) {
            Optional<ProductPrice> productPriceIsActive = getProductPrice(product);

            if (productPriceIsActive.isEmpty()) continue;

            Vendor vendor = productPriceIsActive.get().getVendor();

            productResponses.add(buildProductResponse(vendor, product.getCategory(), product, productPriceIsActive.get()));
        }

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }


    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse update(ProductRequest request) {
        Product product = findById(request.getId());
        Vendor vendor = vendorService.getById(request.getVendorId());
        Category category = categoryService.getOrSave(request.getCategory());

        Optional<ProductPrice> productPriceIsActive = getProductPrice(product);

        product.setName(request.getProductName());
        product.setCategory(category);

        if (productPriceIsActive.isPresent() && request.getPrice().equals(productPriceIsActive.get().getPrice())) {
            productPriceIsActive.get().setStock(request.getStock());

            productRepository.save(product);

            return buildProductResponse(vendor, product.getCategory(), product, productPriceIsActive.get());
        }

        productPriceIsActive.ifPresent(productPrice -> productPrice.setIsActive(false));

        ProductPrice newProductPrice = ProductPrice.builder()
                .price(request.getPrice())
                .stock(request.getStock())
                .product(product)
                .vendor(vendor)
                .isActive(true)
                .build();

        productPriceService.create(newProductPrice);

        return buildProductResponse(vendor, product.getCategory(), product, newProductPrice);
    }

    @Override
    public void delete(String id) {
        Product product = findById(id);
        product.setIsActive(false);
    }

    @Override
    public Product findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));
        if (product.getIsActive().equals(false))
            throw new ResponseStatusException(HttpStatus.GONE, "Product Was Not Active");

        return product;
    }

    private static Optional<ProductPrice> getProductPrice(Product product) {
        return product.getProductPrices()
                .stream()
                .filter(ProductPrice::getIsActive).findFirst();
    }

    private String generateProductCode() {
        long productSize = productRepository.findAll().size();
        return String.format("KB%04d", productSize + 1);
    }

    private static ProductResponse buildProductResponse(Vendor vendor, Category category, Product product, ProductPrice productPrice) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getName())
                .category(category.getCategory())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .vendorName(vendor.getName())
                .build();
    }
}
