package com.varc.brewnetapp.domain.returning.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.returning.command.application.service.ReturningService;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo.ReturningReqVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("FrReturningControllerCommand")
@RequiredArgsConstructor
@RequestMapping("/api/v1/franchise/return")
@Slf4j
public class FrReturningController {

    private final ReturningService returningService;

    @PostMapping("/")
    @Operation(summary = "[가맹점] 반품신청 API")
    public ResponseEntity<ResponseMessage<Integer>> registReturning(@RequestAttribute("loginId") String loginId,
                                                                   @RequestPart("returningReqVO") ReturningReqVO returningReqVO,
                                                                   @RequestParam("returningImage") List<MultipartFile> returningImageList
    ) {
        int returningCode = returningService.franCreateReturning(loginId, returningReqVO, returningImageList);
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품신청 성공", returningCode));
    }
}
