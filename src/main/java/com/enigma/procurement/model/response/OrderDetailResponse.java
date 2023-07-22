package com.enigma.procurement.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class OrderDetailResponse {
    private String id;
    private String vendor;
    private String name;
    private String category;
    private Integer quantity;
    private Long price;
    private Long subTotal;

}
