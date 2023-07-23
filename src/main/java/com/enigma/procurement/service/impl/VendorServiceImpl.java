package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Vendor;
import com.enigma.procurement.repository.VendorRepository;
import com.enigma.procurement.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;

    @Override
    public Vendor create(Vendor request) {
        return vendorRepository.saveAndFlush(request);
    }

    @Override
    public Page<Vendor> getAll(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Vendor> vendors = vendorRepository.findAll(pageable);
        return new PageImpl<>(vendors.getContent(), pageable, vendors.getTotalElements());
    }

    @Override
    public Vendor getById(String id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor Not Found"));
    }
}
