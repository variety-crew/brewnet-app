package com.varc.brewnetapp.domain.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/check")
public class CheckController {

    @GetMapping
    public String check() {
        return "Good";
    }

}
