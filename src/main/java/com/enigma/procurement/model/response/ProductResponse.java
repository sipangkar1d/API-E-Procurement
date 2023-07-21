package com.enigma.procurement.model.response;

import lombok.Builder;

@Builder(toBuilder = true)
public class ProductResponse {
    String id;
    String productName;
    String category;
    Long price;
    Integer stock;
    String vendorName;
}
