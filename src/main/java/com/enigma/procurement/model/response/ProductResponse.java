package com.enigma.procurement.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
public class ProductResponse {
    private String productName;
    private String productCode;
    private String category;
    private Long price;
    private String vendorName;
}
