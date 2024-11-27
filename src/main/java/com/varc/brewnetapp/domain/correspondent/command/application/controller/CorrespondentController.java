package com.varc.brewnetapp.domain.correspondent.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.correspondent.command.application.dto.CorrespondentRequestDTO;
import com.varc.brewnetapp.domain.correspondent.command.application.service.CorrespondentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("CorrespondentControllerCommand")
@RequestMapping("api/v1/hq/correspondent")
public class CorrespondentController {

    private final CorrespondentService correspondentService;

    @Autowired
    public CorrespondentController(CorrespondentService correspondentService) {
        this.correspondentService = correspondentService;
    }

    @PostMapping("/create")
    @Operation(summary = "거래처 등록 API")
    public ResponseEntity<ResponseMessage<Object>> createCorrespondent(
                                                        @RequestAttribute("loginId") String loginId,
                                                        @RequestBody CorrespondentRequestDTO newCorrespondent) {

        correspondentService.createCorrespondent(loginId, newCorrespondent);

        return ResponseEntity.ok(new ResponseMessage<>(200, "거래처 등록 성공", null));
    }

    @PutMapping("/edit/{correspondentCode}")
    @Operation(summary = "거래처 정보 수정 API")
    public ResponseEntity<ResponseMessage<Object>> updateCorrespondent(
                                                        @RequestAttribute("loginId") String loginId,
                                                        @PathVariable int correspondentCode,
                                                        @RequestBody CorrespondentRequestDTO editCorrespondent) {

        correspondentService.updateCorrespondent(loginId, correspondentCode, editCorrespondent);

        return ResponseEntity.ok(new ResponseMessage<>(200, "거래처 정보 수정 완료", null));
    }
}
