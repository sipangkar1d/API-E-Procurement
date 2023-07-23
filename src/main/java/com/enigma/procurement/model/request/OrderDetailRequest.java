package com.enigma.procurement.model.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class OrderDetailRequest {
    @NotBlank(message = "Required Product Price Id")
    private String productPriceId;
    @NotNull(message = "Required quantity")
    private Integer quantity;
}
