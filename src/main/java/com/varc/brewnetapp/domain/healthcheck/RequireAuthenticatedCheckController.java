package com.varc.brewnetapp.domain.healthcheck;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/require-auth")
public class RequireAuthenticatedCheckController {

    @GetMapping
    public String check() {
        return "Authenticated";
    }

    @GetMapping("/master")
    public String checkMaster(@RequestAttribute("loginId") String loginId) {
        return "id: " + loginId + ", Master Authorized";
    }
}
