package com.varc.brewnetapp.domain.item.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.item.command.application.dto.createSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.deleteSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.updateSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commandCategoryController")
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/sub")
    @Operation(summary = "서브 카테고리 생성 API")
    public ResponseEntity<ResponseMessage<List<createSubCategoryRequestDTO>>> createSubCategory() {

        return ResponseEntity.ok(new ResponseMessage<>(200, "서브 카테고리 생성 성공", categoryService.createSubCategory()));
    }

    @PutMapping("/sub")
    @Operation(summary = "서브 카테고리 수정 API")
    public ResponseEntity<ResponseMessage<List<updateSubCategoryRequestDTO>>> updateCategory() {

        return ResponseEntity.ok(new ResponseMessage<>(200, "서브 카테고리 수정 성공", categoryService.updateCategory()));
    }

    @DeleteMapping("/sub")
    @Operation(summary = "서브 카테고리 삭제 API")
    public ResponseEntity<ResponseMessage<List<deleteSubCategoryRequestDTO>>> deleteCategory() {

        return ResponseEntity.ok(new ResponseMessage<>(200, "서브 카테고리 삭제 성공", categoryService.deleteCategory()));
    }
}

