package com.enigma.procurement.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class DownloadResponse {
    private String productCode;
    private String orderDate;
    private String vendor;
    private String productName;
    private String category;
    private Long price;
    private Integer quantity;
    private Long subTotal;
}
