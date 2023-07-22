package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.UserDetailsImpl;
import com.enigma.procurement.model.request.AuthRequest;
import com.enigma.procurement.model.response.LoginResponse;
import com.enigma.procurement.security.JwtUtils;
import com.enigma.procurement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(AuthRequest request) {
        System.out.println(request);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        System.out.println(authenticate.getName());

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
}

