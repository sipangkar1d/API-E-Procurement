package com.enigma.procurement.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ProductRequest {
    String id;
    String productName;
    String category;
    Long price;
    Integer stock;
    String vendorId;
}
