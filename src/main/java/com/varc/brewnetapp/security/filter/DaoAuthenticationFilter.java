package com.varc.brewnetapp.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varc.brewnetapp.security.provider.ProviderManager;
import com.varc.brewnetapp.security.utility.JwtUtil;
import com.varc.brewnetapp.security.vo.LoginRequestVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
public class DaoAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    @Autowired
    public DaoAuthenticationFilter(
            ProviderManager providerManager,
            JwtUtil jwtUtil
    ) {
        this.setAuthenticationManager(providerManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestVO loginRequestVO = new ObjectMapper().readValue(request.getInputStream(), LoginRequestVO.class);
            log.debug("loginRequestVO: {}", loginRequestVO);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestVO.getLoginId(), loginRequestVO.getPassword(), new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    )
    {
        String accessToken = jwtUtil.generateAccessToken(authResult);
        String refreshToken = jwtUtil.generateRefreshToken(authResult);
        log.debug("refreshToken: {}", refreshToken);
        log.debug("accessToken: {}", accessToken);

        response.addHeader("Access-Token", accessToken);
        response.addHeader("Refresh-Token", refreshToken);
    }
}
