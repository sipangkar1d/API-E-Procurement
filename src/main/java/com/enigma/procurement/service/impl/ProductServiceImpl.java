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
import java.util.stream.Collectors;

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
        Vendor vendor = vendorService.getById(request.getId());
        Category category = categoryService.getOrSave(request.getCategory());

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
    public Page<ProductResponse> getAll(String name, Long maxPrice, Integer page, Integer size) {
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            Join<Product, ProductPrice> productPrices = root.join("productPrices");
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPrices.get("price"), maxPrice));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };


        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(specification, pageable);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products.getContent()) {
            Optional<ProductPrice> productPrice = getProductPrice(product);

            if (productPrice.isEmpty()) continue;
            productResponses.add(buildProductResponse(productPrice.get().getVendor(), product.getCategory(), product, productPrice.get()));
        }

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }


    @Override
    public ProductResponse update(ProductRequest request) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));
    }

    private static Optional<ProductPrice> getProductPrice(Product product) {
        return product.getProductPrices()
                .stream()
                .filter(ProductPrice::getIsActive).findFirst();
    }

    private String generateProductCode() {
        int productSize = productRepository.countAll();
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
