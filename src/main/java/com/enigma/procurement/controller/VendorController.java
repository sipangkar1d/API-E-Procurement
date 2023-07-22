package com.enigma.procurement.controller;

import com.enigma.procurement.entity.Vendor;
import com.enigma.procurement.model.request.ProductRequest;
import com.enigma.procurement.model.response.CommonResponse;
import com.enigma.procurement.service.ProductService;
import com.enigma.procurement.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/vendor")
public class VendorController {
    private final VendorService vendorService;

    @PostMapping()
    public ResponseEntity<?> createNewProduct(@RequestBody Vendor request) {
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Product success created")
                .data(vendorService.create(request))
                .build();
        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }


}
