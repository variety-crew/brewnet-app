package com.varc.brewnetapp.utility.servlet;

import com.varc.brewnetapp.security.utility.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CustomJwtFilter implements Filter {
    private final JwtUtil jwtUtil;

    public CustomJwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = ((HttpServletRequest) servletRequest);
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String loginId = jwtUtil.getLoginId(
                    authorizationHeader.replace("Bearer ", "")
            );
            if (loginId != null) {
                httpServletRequest.setAttribute("loginId", loginId);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
