package com.enigma.procurement.security;

import com.enigma.procurement.model.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
                .message(authException.getMessage())
                .build();
        String commonResponseString = objectMapper.writeValueAsString(commonResponse);

        response.setContentType("application/json");
        response.setStatus(commonResponse.getStatusCode());
        response.getWriter().write(commonResponseString);
    }

}
