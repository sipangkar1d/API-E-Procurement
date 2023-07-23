package com.enigma.procurement.controller;

import com.enigma.procurement.model.request.ProductRequest;
import com.enigma.procurement.model.response.CommonResponse;
import com.enigma.procurement.model.response.PagingResponse;
import com.enigma.procurement.model.response.ProductResponse;
import com.enigma.procurement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest request){
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Create Product")
                .data(productService.create(request))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping()
    public ResponseEntity<?> getAllProduct(
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
    ){
        Page<ProductResponse> responses = productService.getAll(page, size);
        PagingResponse paging = PagingResponse.builder()
                .currentPage(page)
                .totalPage(responses.getTotalPages())
                .size(responses.getSize())
                .build();
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Create Product")
                .data(responses.getContent())
                .paging(paging)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
