package com.enigma.procurement.controller;

import com.enigma.procurement.model.response.CommonResponse;
import com.enigma.procurement.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/vendor")
public class VendorController {
    private final VendorService vendorService;
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllVendor(
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
    ){
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Get All Vendor")
                .data(vendorService.getAll(size,page).getContent())
                .build();
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(commonResponse);
    }
}
