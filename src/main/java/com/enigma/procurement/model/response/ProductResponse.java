package com.enigma.procurement.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
public class ProductResponse {
    String id;
    String productName;
    String category;
    Long price;
    Integer stock;
    String vendorName;
}
