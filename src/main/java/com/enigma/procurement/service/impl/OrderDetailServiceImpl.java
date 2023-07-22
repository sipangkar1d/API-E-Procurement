package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.OrderDetail;
import com.enigma.procurement.repository.OrderDetailRepository;
import com.enigma.procurement.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    @Override
    public OrderDetail create(OrderDetail request) {
        return orderDetailRepository.saveAndFlush(request);
    }
}
