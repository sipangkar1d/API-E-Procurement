package com.enigma.procurement.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "Required Product Name")
    private String productName;
    @NotBlank(message = "Required Category")
    private String category;
    @NotNull(message = "Required Price")
    private Long price;
    @NotBlank(message = "Required Vendor Id")
    private String vendorId;
}
