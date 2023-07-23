package com.enigma.procurement;

import com.enigma.procurement.entity.Admin;
import com.enigma.procurement.entity.Role;
import com.enigma.procurement.entity.UserCredential;
import com.enigma.procurement.entity.constant.ERole;
import com.enigma.procurement.repository.UserCredentialRepository;
import com.enigma.procurement.security.BCryptUtils;
import com.enigma.procurement.service.AdminService;
import com.enigma.procurement.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class ProcurementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcurementApplication.class, args);
    }
}


