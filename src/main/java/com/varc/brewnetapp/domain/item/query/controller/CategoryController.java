package com.varc.brewnetapp.domain.item.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.item.query.dto.ItemDTO;
import com.varc.brewnetapp.domain.item.query.dto.SubCategoryDTO;
import com.varc.brewnetapp.domain.item.query.service.CategoryService;
import com.varc.brewnetapp.domain.item.query.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
}
