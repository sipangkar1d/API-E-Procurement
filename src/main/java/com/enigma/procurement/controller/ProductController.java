package com.enigma.procurement.controller;

import com.enigma.procurement.model.request.ProductRequest;
import com.enigma.procurement.model.response.CommonResponse;
import com.enigma.procurement.model.response.ProductResponse;
import com.enigma.procurement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<?> createNewProduct(@RequestBody(required = false) ProductRequest request) {
        ProductResponse productResponse = productService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .data(productResponse)
                        .message("Success Created")
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }
}
