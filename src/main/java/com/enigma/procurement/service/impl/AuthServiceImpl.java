package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.*;
import com.enigma.procurement.entity.constant.ERole;
import com.enigma.procurement.model.request.AuthRequest;
import com.enigma.procurement.model.request.AuthVendorRequest;
import com.enigma.procurement.model.response.LoginResponse;
import com.enigma.procurement.model.response.ProductResponse;
import com.enigma.procurement.model.response.RegisterResponse;
import com.enigma.procurement.model.response.RegisterVendorResponse;
import com.enigma.procurement.repository.UserCredentialRepository;
import com.enigma.procurement.security.BCryptUtils;
import com.enigma.procurement.security.JwtUtils;
import com.enigma.procurement.service.*;
import com.enigma.procurement.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;
    private final BCryptUtils bCryptUtils;
    private final UserCredentialRepository userCredentialRepository;
    private final AdminService adminService;
    private final ValidationUtil validationUtil;
    private final VendorService vendorService;
    private final ProductService productService;

    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserDetailsImpl userDetail = (UserDetailsImpl) authenticate.getPrincipal();

        List<String> roles = userDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtUtils.generateToken(userDetail.getEmail());
        return LoginResponse.builder()
                .username(userDetail.getEmail())
                .roles(roles)
                .token(token)
                .build();
    }


    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        try {
            validationUtil.validate(request);
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Admin admin = Admin.builder()
                    .email(userCredential.getEmail())
                    .name(userCredential.getEmail().substring(0, userCredential.getEmail().indexOf("@")))
                    .userCredential(userCredential)
                    .build();
            adminService.create(admin);

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .build();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exist");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterVendorResponse registerVendor(AuthVendorRequest request) {
        try {
            validationUtil.validate(request);
            Role role = roleService.getOrSave(ERole.ROLE_VENDOR);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Vendor vendor = vendorService.create(
                    Vendor.builder()
                            .address(request.getAddress())
                            .mobilePhone(request.getMobilePhone())
                            .email(userCredential.getEmail())
                            .name(request.getName())
                            .userCredential(userCredential)
                            .build());

            List<ProductResponse> productResponses = request.getProducts().stream().map(productRequest -> {
                productRequest.setVendorId(vendor.getId());
                return productService.create(productRequest);
            }).collect(Collectors.toList());

            return RegisterVendorResponse.builder()
                    .name(vendor.getName())
                    .email(vendor.getEmail())
                    .products(productResponses)
                    .build();

        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Vendor already exist");
        }
    }
}

