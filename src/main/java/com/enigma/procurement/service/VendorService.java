package com.enigma.procurement.service;

import com.enigma.procurement.entity.Vendor;

import java.util.List;

public interface VendorService {
    Vendor create(Vendor request);
    List<Vendor> getAll();
    Vendor getById(String id);
}
