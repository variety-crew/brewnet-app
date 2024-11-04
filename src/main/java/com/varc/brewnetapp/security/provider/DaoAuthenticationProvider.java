package com.varc.brewnetapp.security.provider;

import com.varc.brewnetapp.domain.auth.query.service.AuthService;
import com.varc.brewnetapp.security.service.RefreshTokenService;
import com.varc.brewnetapp.security.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DaoAuthenticationProvider implements AuthenticationProvider {
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    public DaoAuthenticationProvider(
            AuthService authService,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            RefreshTokenService refreshTokenService,
            JwtUtil jwtUtil
    ) {
        this.authService = authService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getPrincipal().toString();
        String loginPassword = authentication.getCredentials().toString();

        UserDetails savedUser = authService.loadUserByUsername((authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName());

        if (!bCryptPasswordEncoder.matches(loginPassword, savedUser.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        } else {
            Authentication authenticationResult = new UsernamePasswordAuthenticationToken(savedUser, savedUser.getPassword(), savedUser.getAuthorities());
            String refreshToken = jwtUtil.generateRefreshToken(authenticationResult);
            refreshTokenService.saveRefreshToken(loginId, refreshToken);
            return authenticationResult;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
