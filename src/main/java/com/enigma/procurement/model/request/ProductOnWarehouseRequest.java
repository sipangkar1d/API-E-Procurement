package com.enigma.procurement.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(toBuilder = true)
public class ProductOnWarehouseRequest {
    private String name;
    private String productCode;
    private Integer quantity;
}
