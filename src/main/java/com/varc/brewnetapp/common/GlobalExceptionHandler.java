package com.varc.brewnetapp.common;

import com.varc.brewnetapp.exception.DuplicateException;
import com.varc.brewnetapp.exception.InvalidEmailCodeException;
import com.varc.brewnetapp.exception.InvalidEmailException;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 400: 잘못된 요청 예외 처리
    @ExceptionHandler({
        DuplicateException.class,
        DuplicateException.class,
        InvalidEmailCodeException.class,
        InvalidEmailException.class,
        InvalidDataException.class,
        MemberNotFoundException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponseMessage<>(400, e.getMessage(), null));
    }

    // 401: 권한 없는 사용자
    @ExceptionHandler({
        UnauthorizedAccessException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleUnAuthorizedException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseMessage<>(401, e.getMessage(), null));
    }

}

