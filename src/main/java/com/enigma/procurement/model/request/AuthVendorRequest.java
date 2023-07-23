package com.enigma.procurement.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthVendorRequest {
    @NotBlank(message = "Required Vendor Name")
    private String name;

    @NotBlank(message = "Required email")
    private String email;

    @NotBlank(message = "Required password")
    private String password;

    @NotBlank(message = "Required Vendor mobile phone")
    private String mobilePhone;

    @NotBlank(message = "Required Vendor address")
    private  String address;

    private List<ProductRequest> products;
}
