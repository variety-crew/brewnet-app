package com.varc.brewnetapp.domain.item.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.item.query.dto.CategoryDTO;
import com.varc.brewnetapp.domain.item.query.dto.SubCategoryDTO;
import com.varc.brewnetapp.domain.item.query.dto.SuperCategoryDTO;
import com.varc.brewnetapp.domain.item.query.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "queryCategoryController")
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/sub")
    @Operation(summary = "서브 카테고리 조회 API")
    public ResponseEntity<ResponseMessage<List<SubCategoryDTO>>> findSubCategory() {
        
        return ResponseEntity.ok(new ResponseMessage<>(200, "서브 카테고리 조회 성공", categoryService.findSubCategory()));
    }

    @GetMapping("/{superCategoryCode}/sub")
    @Operation(summary = "특정 슈퍼 카테고리의 서브 카테고리 조회 API")
    public ResponseEntity<ResponseMessage<List<SubCategoryDTO>>> findSubCategoryBySuperCategory(
        @PathVariable(value ="superCategoryCode", required = false) int superCategoryCode
    ) {

        return ResponseEntity.ok(new ResponseMessage<>(
            200, "서브 카테고리 조회 성공", categoryService.findSubCategoryBySuperCategory(superCategoryCode)));
    }

    @GetMapping("")
    @Operation(summary = "하위 카테고리 목록(상위 카테고리 이름 포함) 조회 API")
    public ResponseEntity<ResponseMessage<List<CategoryDTO>>> findCategory() {

        return ResponseEntity.ok(new ResponseMessage<>(200, "카테고리 조회 성공", categoryService.findCategory()));
    }

    @GetMapping("/super")
    @Operation(summary = "상위 카테고리 목록 조회 API")
    public ResponseEntity<ResponseMessage<List<SuperCategoryDTO>>> findSuperCategory() {

        return ResponseEntity.ok(new ResponseMessage<>(200, "상위 카테고리 조회 성공", categoryService.findSuperCategory()));
    }
}
