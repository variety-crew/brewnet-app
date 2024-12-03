package com.varc.brewnetapp.domain.franchise.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.franchise.command.application.dto.CreateFranchiseRequestDTO;
import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseDTO;
import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseMemberDTO;
import com.varc.brewnetapp.domain.franchise.query.service.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    public ResponseEntity<ResponseMessage<FranchiseDTO>> findFranchise(@PathVariable Integer franchiseCode) {


        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 정보 상세 조회 성공", franchiseService.findFranchise(franchiseCode)));
    }

    @GetMapping("")
    @Operation(summary = "가맹점 정보 목록 조회 API / query param으로 page와 size를 키값으로 데이터 보내주시면 됩니다 "
        + "/ page는 0부터 시작 / citys는 도/시로 필터링. 필수 X. 여러개 가능 / franchiseName은 지점명. 필수 X " 
        + "/ sort는 franchiseNameASC나 franchiseNameDESC만 보내주세요")
    public ResponseEntity<ResponseMessage<Page<FranchiseDTO>>> findFranchiseList(@PageableDefault(page = 0, size = 10) Pageable page,
        @RequestParam(required = false) String franchiseName,
        @RequestParam(required = false) List<String> citys,
        @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "가맹점 정보 조회 성공", franchiseService.findFranchiseList(page, franchiseName, citys, sort)));
    }

    @GetMapping("/excel")
    @Operation(summary = "엑셀 출력용 가맹점 목록 정보 조회 API / "
        + " citys는 도/시로 필터링. 필수 X. 여러개 가능 / franchiseName은 지점명. 필수 X "
        + "/ sort는 franchiseNameASC나 franchiseNameDESC만 보내주세요")
    public ResponseEntity<ResponseMessage<List<FranchiseDTO>>> findFranchiseListExcel(
        @RequestParam(required = false) String franchiseName,
        @RequestParam(required = false) List<String> citys,
        @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "엑셀 출력용 목록 가맹점 정보 조회 성공", franchiseService.findFranchiseListExcel(franchiseName, citys, sort)));
    }

    @GetMapping("/member")
    @Operation(summary = "가맹점 회원 조회 API / query param으로 page와 size를 키값으로 데이터 보내주시면 됩니다 "
        + "/ page는 0부터 시작 / citys는 도/시로 필터링. 필수 X. 여러개 가능 / franchiseName은 지점명. 필수 X "
        + "/ sort는 franchiseNameASC나 franchiseNameDESC만 보내주세요")
    public ResponseEntity<ResponseMessage<Page<FranchiseMemberDTO>>> findFranchiseMemberList(@PageableDefault(page = 0, size = 10) Pageable page,
        @RequestParam(required = false) String franchiseName,
        @RequestParam(required = false) List<String> citys,
        @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "가맹점 회원 조회 성공", franchiseService.findFranchiseMemberList(page, franchiseName, citys, sort)));
    }


}
