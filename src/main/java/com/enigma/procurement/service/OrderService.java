package com.enigma.procurement.service;

import com.enigma.procurement.entity.Order;

import java.util.List;

public interface OrderService {
    Order create(Order request);
    List<Order> getByDay();
    List<Order> getByWeek();
}
