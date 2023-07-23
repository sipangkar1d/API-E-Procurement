package com.enigma.procurement.controller;

import com.enigma.procurement.entity.ProductPrice;
import com.enigma.procurement.model.request.ProductPriceRequest;
import com.enigma.procurement.model.response.CommonResponse;
import com.enigma.procurement.model.response.ProductResponse;
import com.enigma.procurement.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/product-price")
public class ProductPriceController {
    private final ProductPriceService productPriceService;

    @PutMapping()
     public ResponseEntity<?> updateProductPrice(@RequestBody ProductPriceRequest request){
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Update Product Price")
                .data(productPriceService.update(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
