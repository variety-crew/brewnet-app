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
        log.debug("authException: {}", authException);
        String errorMessage = getErrorMessage(authException);

        ResponseMessage<String> responseMessage = new ResponseMessage<>(
                STATUS_CODE_401,
                errorMessage,
                null
        );

        // 응답 헤더 및 상태 코드 설정
        response.setStatus(STATUS_CODE_401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ResponseMessage 객체를 JSON으로 변환 후 응답에 작성
        String jsonResponse = objectMapper.writeValueAsString(responseMessage);
        response.getWriter().write(jsonResponse);
    }

    private static String getErrorMessage(AuthenticationException authException) {
        String errorMessage;
        Throwable cause = authException.getCause();

        if (cause instanceof SecurityException || cause instanceof MalformedJwtException) { // jwt 유효성 통과 실패
            errorMessage = "invalid token";
        } else if (cause instanceof ExpiredJwtException) { // jwt 토큰 만료
            errorMessage = "token expired";
        } else if (cause instanceof UnsupportedJwtException) { // 지원하지 않는 jwt 토큰
            errorMessage = "unsupported token";
        } else {    // 예기치못한 에러
            errorMessage = "unexpected error during authentication";
        }
        return errorMessage;
    }
}
