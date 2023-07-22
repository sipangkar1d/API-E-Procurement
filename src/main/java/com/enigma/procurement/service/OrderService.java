package com.enigma.procurement.service;

import com.enigma.procurement.entity.Order;
import com.enigma.procurement.model.request.OrderRequest;
import com.enigma.procurement.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    List<Order> getByDay();
    List<Order> getByWeek();
}
