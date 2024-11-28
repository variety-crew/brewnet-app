package com.varc.brewnetapp.domain.item.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commandCategoryController")
@RequestMapping("api/v1/category")
public class CategoryController {


//
//    @GetMapping("/sub")
//    @Operation(summary = "서브 카테고리 조회 API")
//    public ResponseEntity<ResponseMessage<List<SubCategoryDTO>>> findSubCategory() {
//
//        return ResponseEntity.ok(new ResponseMessage<>(200, "서브 카테고리 조회 성공", categoryService.findSubCategory()));
//    }
//
//    @GetMapping("")
//    @Operation(summary = "하위 카테고리 목록(상위 카테고리 이름 포함) 조회 API")
//    public ResponseEntity<ResponseMessage<List<CategoryDTO>>> findCategory() {
//
//        return ResponseEntity.ok(new ResponseMessage<>(200, "카테고리 조회 성공", categoryService.findCategory()));
//    }
}

