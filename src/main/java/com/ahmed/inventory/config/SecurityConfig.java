package com.ahmed.inventory.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("GLOBAL_ADMIN")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new HeaderRoleFilter(), AnonymousAuthenticationFilter.class);

        return http.build();
    }

    static class HeaderRoleFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {

            String role = request.getHeader("X-Role");

            if (role != null && !role.isBlank()) {
                AbstractAuthenticationToken authentication =
                        new AbstractAuthenticationToken(
                                AuthorityUtils.createAuthorityList("ROLE_" + role)
                        ) {
                            @Override
                            public Object getCredentials() {
                                return null;
                            }

                            @Override
                            public Object getPrincipal() {
                                return "header-user";
                            }
                        };

                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }
    }
}