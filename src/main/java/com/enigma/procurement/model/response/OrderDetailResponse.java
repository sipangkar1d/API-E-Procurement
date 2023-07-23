package com.enigma.procurement.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class OrderDetailResponse {
    private String name;
    private String vendor;
    private Integer quantity;
    private Long price;
    private Long subTotal;

}
