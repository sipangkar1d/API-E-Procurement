package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Vendor;
import com.enigma.procurement.repository.VendorRepository;
import com.enigma.procurement.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    @Override
    public Vendor create(Vendor request) {
        return vendorRepository.saveAndFlush(request);
    }

    @Override
    public List<Vendor> getAll() {
        return null;
    }

    @Override
    public Vendor getById(String id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor Not Found"));
    }
}
