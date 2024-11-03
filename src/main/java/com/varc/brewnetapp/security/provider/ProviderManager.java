package com.varc.brewnetapp.security.provider;

import com.varc.brewnetapp.security.exception.NotAuthenticatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProviderManager implements AuthenticationManager {
    private final List<AuthenticationProvider> providerList;

    @Autowired
    public ProviderManager(List<AuthenticationProvider> providerList) {
        this.providerList = providerList;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        providerList.forEach(provider -> {
            if (provider.supports(authentication.getClass())) {
                provider.authenticate(authentication);
            }
        });

        // 인증 실패 예와
        throw new NotAuthenticatedException("Adequate provider not found");
    }
}
