package com.enigma.procurement.service;

import com.enigma.procurement.entity.Admin;

public interface AdminService {
    Admin createAdmin(Admin request);
    Admin update(Admin request);
    Admin findById(String id);

}
