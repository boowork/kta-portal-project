package com.kta.portal.admin.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final Environment environment;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, Environment environment) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        // 이미 인증이 있으면 (예: @WithMockUser) JWT 처리 건너뛰기
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Development authentication header check first (only in dev profiles)
        String devAuth = getDevAuthFromRequest(request);
        if (StringUtils.hasText(devAuth) && isDevProfileActive()) {
            processDevAuthentication(devAuth);
        } else {
            // Regular JWT authentication
            String token = getTokenFromRequest(request);
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                Claims claims = jwtTokenProvider.getClaimsFromToken(token);
                String userid = claims.get("userid", String.class);

                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userid,
                        null,
                        Collections.emptyList()
                    );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getDevAuthFromRequest(HttpServletRequest request) {
        return request.getHeader("DEV_AUTH");
    }
    
    /**
     * Check if development profiles are active
     * Allows DEV_AUTH header only in: default, local, dev profiles
     */
    private boolean isDevProfileActive() {
        String[] activeProfiles = environment.getActiveProfiles();
        
        // If no profiles are active, assume default profile
        if (activeProfiles.length == 0) {
            return true; // default profile
        }
        
        // Check if any of the allowed dev profiles are active
        String[] allowedDevProfiles = {"default", "local", "dev", "test"};
        return Arrays.stream(activeProfiles)
                .anyMatch(profile -> Arrays.asList(allowedDevProfiles).contains(profile));
    }
    
    private void processDevAuthentication(String devAuth) {
        try {
            // Parse DEV_AUTH header format: {id}:{user_id}:{name}
            String[] parts = devAuth.split(":");
            if (parts.length == 3) {
                String id = parts[0];
                String userid = parts[1];
                String name = parts[2];
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userid,
                        null,
                        Collections.emptyList()
                    );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Invalid DEV_AUTH format, ignore and proceed with normal flow
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}