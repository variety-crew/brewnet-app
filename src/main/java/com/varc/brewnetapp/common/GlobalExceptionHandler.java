package com.varc.brewnetapp.common;

import com.varc.brewnetapp.exception.*;
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
            MemberNotFoundException.class,
            InvalidStatusException.class,
            InvalidApiRequestException.class,
            InvalidConditionException.class,
            MemberNotInFranchiseException.class,
            InvalidOrderItems.class,
            OrderApprovalAlreadyExist.class,
            OrderDraftAlreadyApproved.class,
            UnexpectedOrderStatus.class,
            IllegalArgumentException.class,
    })
    public ResponseEntity<ResponseMessage<Object>> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage<>(400, e.getMessage(), null));
    }

    // 401: 권한 없는 사용자
    @ExceptionHandler({
            AccessDeniedException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleUnAuthorizedException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseMessage<>(401, e.getMessage(), null));
    }

    // 403: FORBIDDEN
    @ExceptionHandler({
            UnauthorizedAccessException.class,
            NoAccessAuthoritiesException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleNoAccessException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseMessage<>(
                        HttpStatus.FORBIDDEN.value(),
                        e.getMessage(),
                        null));
    }

    // 404: Not Found
    @ExceptionHandler({
            ExchangeNotFoundException.class,
            EmptyDataException.class,
            ApprovalNotFoundException.class,
            CorrespondentNotFoundException.class,
            ItemNotFoundException.class,
            OrderNotFound.class,
            PositionNotFoundException.class,
            PurchaseNotFoundException.class,
            SealNotFoundException.class,
            StorageNotFoundException.class,
            OrderApprovalNotFound.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage<>(404, e.getMessage(), null));
    }

    // Unexpected Exception
    @ExceptionHandler({
            RuntimeException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleUnexpectedException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(new ResponseMessage<>(500, "Server Error: 관리자에게 문의하세요.", null));
    }
}

