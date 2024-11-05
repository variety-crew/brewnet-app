package com.varc.brewnetapp.security.provider;

import com.varc.brewnetapp.domain.auth.query.service.AuthService;
import com.varc.brewnetapp.security.service.RefreshTokenService;
import com.varc.brewnetapp.security.utility.JwtUtil;
import com.varc.brewnetapp.security.utility.token.JwtAuthenticationRefreshToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;
    private final HttpServletResponse response;

    public RefreshTokenAuthenticationProvider(
            JwtUtil jwtUtil,
            RefreshTokenService refreshTokenService,
            AuthService authService,
            HttpServletResponse response
            ) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.authService = authService;
        this.response = response;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        String loginId = jwtUtil.getLoginId(token);
        if (refreshTokenService.checkRefreshTokenInRedis(loginId, token)) {
            if (jwtUtil.isTokenValid(token)) {
                try {
                    UserDetails savedUser = authService.loadUserByUsername(loginId);
                    Authentication authResult =  new UsernamePasswordAuthenticationToken(savedUser, savedUser.getPassword(), savedUser.getAuthorities());
                    String accessToken = jwtUtil.generateAccessToken(authResult);
                    response.setHeader("Authorization", "Bearer " + accessToken);

                } catch (Exception e) {
                    throw new IllegalArgumentException("user not found", e);
                }

                return jwtUtil.getAuthentication(token);
            } else {
                throw new IllegalArgumentException("invalid token");
            }
        } else {
            throw new IllegalArgumentException("refresh token not found");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationRefreshToken.class.isAssignableFrom(authentication);
    }
}
