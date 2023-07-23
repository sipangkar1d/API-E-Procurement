package com.enigma.procurement.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
public class RegisterVendorResponse {
    private String name;
    private String email;
    private List<ProductResponse> products;
}
