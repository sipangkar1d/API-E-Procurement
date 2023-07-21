package com.enigma.procurement.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ProductRequest {
    String id;
    @NotBlank(message = "product name is required")
    String productName;
    @NotBlank(message = "category is required")
    String category;
    @NotNull(message = "price is required")
    Long price;
    @NotNull(message = "stock is required")
    Integer stock;
    @NotBlank(message = "vendor is required")
    String vendorId;
}
