package com.example.promonade.security.jwt;


import com.example.promonade.exceptions.tokenExceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        final String expiredMsg = (String) request.getAttribute("expired");
        final Map<String, Object> body = new HashMap<>();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        body.put("error", "Unauthorized");
        body.put("path", request.getServletPath());

        // Check for TokenExpiredException and set custom status code
        if (expiredMsg!=null) {
            response.setStatus(440); // Custom status code for token expired
            body.put("status", 440);
            body.put("message", "SESSION_EXPIRED");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("message", authException.getMessage());

        }

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

    }
}
