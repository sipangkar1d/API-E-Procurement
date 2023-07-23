package com.enigma.procurement.service;

import com.enigma.procurement.entity.Vendor;
import org.springframework.data.domain.Page;


public interface VendorService {
    Vendor create(Vendor request);
    Page<Vendor> getAll(Integer size, Integer page);
    Vendor getById(String id);
}
