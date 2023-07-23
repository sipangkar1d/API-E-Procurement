package com.enigma.procurement.model.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class OrderRequest {
    @NotBlank(message = "Required Vendor Id")
    private String vendorId;
    private List<OrderDetailRequest> orderDetails;
}
