package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Warehouse;
import com.enigma.procurement.repository.WarehouseRepository;
import com.enigma.procurement.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    @Override
    public Warehouse addOrSave(Warehouse warehouse) {

        return null;
    }

    @Override
    public List<Warehouse> getAllProduct() {
        return null;
    }

    @Override
    public Warehouse getById(String id) {
        return warehouseRepository.findById(id)
                .orElseThrow();
    }
}
