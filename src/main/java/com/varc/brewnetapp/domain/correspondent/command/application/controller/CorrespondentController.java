package com.varc.brewnetapp.domain.correspondent.command.application.controller;

import com.varc.brewnetapp.domain.correspondent.command.application.service.CorrespondentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("CorrespondentControllerCommand")
@RequestMapping("api/v1/hq/correspondent")
public class CorrespondentController {

    private final CorrespondentService correspondentService;

    @Autowired
    public CorrespondentController(CorrespondentService correspondentService) {
        this.correspondentService = correspondentService;
    }
}
