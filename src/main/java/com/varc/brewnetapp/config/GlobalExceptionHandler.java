package com.varc.brewnetapp.config;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.exception.DuplicateException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 400: 잘못된 요청 예외 처리
    @ExceptionHandler({
        DuplicateException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponseMessage<>(400, e.getMessage(), null));
    }

    // 401: 지정한 리소스에 대한 권한이 없다
    @ExceptionHandler({
        JwtException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleInvalidUserException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseMessage<>(401, e.getMessage(), null));
    }

}

