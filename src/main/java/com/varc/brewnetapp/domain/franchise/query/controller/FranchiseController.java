package com.varc.brewnetapp.domain.franchise.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.franchise.command.application.dto.CreateFranchiseRequestDTO;
import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseDTO;
import com.varc.brewnetapp.domain.franchise.query.service.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "queryFranchiseController")
@RequestMapping("api/v1/hq/franchise")
public class FranchiseController {
    private final FranchiseService franchiseService;

    @Autowired
    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @GetMapping("/detail/{franchiseCode}")
    @Operation(summary = "가맹점 정보 상세 조회 API")
    public ResponseEntity<ResponseMessage<FranchiseDTO>> createFranchise(@PathVariable Integer franchiseCode) {


        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 정보 상세 조회 성공", franchiseService.findFranchise(franchiseCode)));
    }
}
