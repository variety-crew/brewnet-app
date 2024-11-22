package com.varc.brewnetapp.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varc.brewnetapp.common.ResponseMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private static final int STATUS_CODE_401 = HttpServletResponse.SC_UNAUTHORIZED;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthenticationEntryPointImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        ResponseMessage<String> responseMessage = new ResponseMessage<>(
                STATUS_CODE_401,
                "token not authenticated",
                null
        );

        // 응답 헤더 및 상태 코드 설정
        response.setStatus(STATUS_CODE_401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ResponseMessage 객체를 JSON으로 변환 후 응답에 작성
        response.getWriter().write(
                objectMapper.writeValueAsString(responseMessage)
        );
    }
}
