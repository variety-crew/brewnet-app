package com.varc.brewnetapp.config;

import com.varc.brewnetapp.security.utility.JwtUtil;
import com.varc.brewnetapp.utility.servlet.CustomJwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtConfiguration(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public FilterRegistrationBean<CustomJwtFilter> setFilter() {
        FilterRegistrationBean<CustomJwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CustomJwtFilter(jwtUtil));
        filterRegistrationBean.setOrder(0);
        filterRegistrationBean.addUrlPatterns("/api/*");
        return filterRegistrationBean;
    }

}
