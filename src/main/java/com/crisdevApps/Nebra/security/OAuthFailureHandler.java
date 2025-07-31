package com.crisdevApps.Nebra.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthFailureHandler implements AuthenticationFailureHandler {

    @Value("${front.url}")
    private String frontEndpoint;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        var frontRedirect = String.format("%s/login/error?error=%s", frontEndpoint, exception.getMessage());
        response.sendRedirect(frontRedirect);
    }
}
