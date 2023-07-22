package com.enigma.procurement.model.response;

import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
public class OrderResponse {
    private String invoice;
    private Date orderDate;
    private List<OrderDetailResponse> orderDetails;
    private Long grandTotal;

}
