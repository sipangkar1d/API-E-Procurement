package com.enigma.procurement.model.request;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    private String customerId;
    private List<OrderDetailRequest> orderDetails;

}
