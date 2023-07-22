package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Admin;
import com.enigma.procurement.entity.Role;
import com.enigma.procurement.entity.UserCredential;
import com.enigma.procurement.entity.UserDetailsImpl;
import com.enigma.procurement.entity.constant.ERole;
import com.enigma.procurement.model.request.AuthRequest;
import com.enigma.procurement.model.response.LoginResponse;
import com.enigma.procurement.model.response.RegisterResponse;
import com.enigma.procurement.repository.UserCredentialRepository;
import com.enigma.procurement.security.BCryptUtils;
import com.enigma.procurement.security.JwtUtils;
import com.enigma.procurement.service.AdminService;
import com.enigma.procurement.service.AuthService;
import com.enigma.procurement.service.RoleService;
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
    private final UserCredentialRepository userCredentialRepository;
    private final RoleService roleService;
    private final BCryptUtils bCryptUtils;
    private final AdminService adminService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);


            Admin admin = Admin.builder()
                    .name(request.getEmail().substring(0, request.getEmail().indexOf("@")))
                    .userCredential(userCredential)
                    .build();
            adminService.create(admin);

            return RegisterResponse.builder()
                    .email(request.getEmail())
                    .build();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exist");
        }
    }

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

        String token = jwtUtils.generateToken(userDetail.getUsername());
        return LoginResponse.builder()
                .username(userDetail.getUsername())
                .roles(roles)
                .token(token)
                .build();
    }
}
