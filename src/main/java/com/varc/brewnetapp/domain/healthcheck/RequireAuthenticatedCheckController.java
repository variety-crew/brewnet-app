package com.varc.brewnetapp.domain.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/require-auth")
public class RequireAuthenticatedCheckController {

    @GetMapping
    public String check() {
        return "Authenticated";
    }

    @GetMapping("/master")
    public String checkMaster() {
        return "Master Authorized";
    }
}
