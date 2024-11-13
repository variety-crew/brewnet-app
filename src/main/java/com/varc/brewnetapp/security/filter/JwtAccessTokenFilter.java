package com.varc.brewnetapp.security.filter;

import com.varc.brewnetapp.security.provider.ProviderManager;
import com.varc.brewnetapp.security.utility.token.JwtAuthenticationAccessToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAccessTokenFilter extends OncePerRequestFilter {
    private final ProviderManager providerManager;

    @Autowired
    public JwtAccessTokenFilter(ProviderManager providerManager) {
        this.providerManager = providerManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        log.debug("JwtAccessTokenFilter called");
        // 헤더가 있는지 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            providerManager.authenticate(
                    new JwtAuthenticationAccessToken(authorizationHeader.replace("Bearer ", ""))
            );
        }
        log.debug("access token 필터 끝");
        filterChain.doFilter(request, response);

    }
}
