package com.crisdevApps.Nebra.security;

import com.crisdevApps.Nebra.dto.outputDto.TokenDTO;
import com.crisdevApps.Nebra.services.interfaces.IThirdPartyUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${front.url}")
    private String frontEndpoint;

    private final IThirdPartyUserService thirdPartyUserService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User principal = oauthToken.getPrincipal();
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String profilePicture = principal.getAttribute("picture");

        TokenDTO tokens = null;

        tokens = thirdPartyUserService.LoginWithThirdParty(email, name, profilePicture);

        String url = String.format("%s/api/auth/google/callback?token=%s&refresh=%s", frontEndpoint, tokens.token(), tokens.refresh());
        response.sendRedirect(url);
    }
}
