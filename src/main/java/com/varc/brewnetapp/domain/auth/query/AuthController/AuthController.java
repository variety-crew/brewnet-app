package com.varc.brewnetapp.domain.auth.query.AuthController;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.query.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "queryAuthController")
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("")
    @Operation(summary = "권한 조회")
    public ResponseEntity<ResponseMessage<List<String>>> getAuths() {
        return ResponseEntity.ok(new ResponseMessage<>(200, "권한 조회 성공",  authService.getAuths()));
    }
}
