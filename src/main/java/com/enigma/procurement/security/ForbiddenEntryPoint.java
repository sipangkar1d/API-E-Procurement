package com.enigma.procurement.security;


import com.enigma.procurement.model.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ForbiddenEntryPoint implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpServletResponse.SC_FORBIDDEN)
                .message(accessDeniedException.getMessage())
                .build();

        String commonResponseString = this.objectMapper.writeValueAsString(commonResponse);

        response.setContentType("application/json");
        response.setStatus(commonResponse.getStatusCode());
        response.getWriter().write(commonResponseString);
    }
}

