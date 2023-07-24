package com.enigma.procurement.controller;

import com.enigma.procurement.entity.ProductOnWarehouse;
import com.enigma.procurement.model.response.CommonResponse;
import com.enigma.procurement.model.response.PagingResponse;
import com.enigma.procurement.service.ProductOnWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/warehouse")
public class ProductOnWarehouseController {
    private final ProductOnWarehouseService productOnWarehouseService;

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getAllProductOnWarehouse(
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
    ) {
        Page<ProductOnWarehouse> product = productOnWarehouseService.getAll(size, page);
        PagingResponse paging = PagingResponse.builder()
                .size(product.getSize())
                .totalPage(product.getTotalPages())
                .currentPage(page)
                .build();

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Get All Product From Warehouse")
                .data(product.getContent())
                .paging(paging)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
