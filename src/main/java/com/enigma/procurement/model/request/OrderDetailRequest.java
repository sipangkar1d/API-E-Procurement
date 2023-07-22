package com.enigma.procurement.model.request;

import lombok.Getter;

@Getter
public class OrderDetailRequest {
    private String productPriceId;
    private Integer quantity;
}
