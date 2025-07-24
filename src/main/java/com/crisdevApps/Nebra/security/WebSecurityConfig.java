package com.crisdevApps.Nebra.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthTokenFilter authTokenFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final OAuthFailureHandler oAuthFailureHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity security) throws Exception {
            security.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth ->{
                        auth.requestMatchers("api/user/create", "api/user/uploadProfilePicture", "api/comment/business-comments/",
                                        "api/business/search", "api/business/category", "api/business/near",
                                        "api/auth/login", "api/auth/logout", "api/auth/refresh",
                                        "api/account/changePassword", "api/account/sendRecoveryLink", "api/account/verifyAccount")
                                .permitAll().anyRequest().authenticated();
                    })
                    .oauth2Login( login -> {
                        login.successHandler(oAuthSuccessHandler);
                        login.failureHandler(oAuthFailureHandler);
                    })
                    .exceptionHandling(ex -> ex
                            .authenticationEntryPoint((request, response, authException) -> {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                            })
                    )
                    .userDetailsService(customUserDetailsService)
                    .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

            return security.build();
    }



}
