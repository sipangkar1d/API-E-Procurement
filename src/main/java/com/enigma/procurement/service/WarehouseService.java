package com.enigma.procurement.service;

import com.enigma.procurement.entity.Warehouse;

import java.util.List;

public interface WarehouseService {
    Warehouse addOrSave(Warehouse warehouse);
    List<Warehouse> getAllProduct();

    Warehouse getById(String id);
}
