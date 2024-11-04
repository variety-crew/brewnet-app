package com.varc.brewnetapp.domain.auth.query.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException;
}
