package com.varc.brewnetapp.security.config;

import com.varc.brewnetapp.security.filter.DaoAuthenticationFilter;
import com.varc.brewnetapp.security.filter.JwtAccessTokenFilter;
import com.varc.brewnetapp.security.provider.ProviderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final ProviderManager providerManager;
    private final JwtAccessTokenFilter jwtAccessTokenFilter;
    private final DaoAuthenticationFilter daoAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(
            ProviderManager providerManager,
            JwtAccessTokenFilter jwtAccessTokenFilter,
            DaoAuthenticationFilter daoAuthenticationFilter
    ) {
        this.providerManager = providerManager;
        this.jwtAccessTokenFilter = jwtAccessTokenFilter;
        this.daoAuthenticationFilter = daoAuthenticationFilter;
        this.daoAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // token 기반 인증으로 csrf, session 비활성화
                .csrf(AbstractHttpConfigurer::disable)  // csrf 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화

                .authenticationManager(providerManager) // authenticationManager 등록

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/check/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAccessTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(daoAuthenticationFilter)
        ;
        return http.build();
    }

}
