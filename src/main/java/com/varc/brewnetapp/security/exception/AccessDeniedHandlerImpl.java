package com.varc.brewnetapp.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varc.brewnetapp.common.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private static final int STATUS_CODE_403 = HttpServletResponse.SC_FORBIDDEN;
    private final ObjectMapper objectMapper;

    @Autowired
    public AccessDeniedHandlerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        ResponseMessage<String> responseMessage = new ResponseMessage<>(
                STATUS_CODE_403,
                "lack of authorizations",
                null
        );

        // 응답 헤더 및 상태 코드 설정
        response.setStatus(STATUS_CODE_403);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ResponseMessage 객체를 JSON으로 변환 후 응답에 작성
        String jsonResponse = objectMapper.writeValueAsString(responseMessage);
        response.getWriter().write(jsonResponse);
    }
}
