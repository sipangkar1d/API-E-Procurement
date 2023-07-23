package com.enigma.procurement.service;

import com.enigma.procurement.model.request.OrderRequest;
import com.enigma.procurement.model.response.DownloadResponse;
import com.enigma.procurement.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    List<DownloadResponse> getByDay();
    List<DownloadResponse> getByMonth(Integer month);
}
