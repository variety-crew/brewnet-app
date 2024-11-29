package com.varc.brewnetapp.domain.item.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.item.command.application.dto.CreateCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.DeleteSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.DeleteSuperCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.UpdateSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.UpdateSuperCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("")
    @Operation(summary = "슈퍼 or 서브 카테고리 생성 API. / superCategoryCode가 없다면 슈퍼 카테고리 생성, " 
        + "superCategoryCode가 있다면 서브 카테고리 생성")
    public ResponseEntity<ResponseMessage<Object>> createCategory(@RequestBody
        CreateCategoryRequestDTO createCategoryRequestDTO) {

        categoryService.createCategory(createCategoryRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "카테고리 생성 성공", null));
    }

    @PutMapping("/sub")
    @Operation(summary = "서브 카테고리 수정 API")
    public ResponseEntity<ResponseMessage<Object>> updateSubCategory(@RequestBody
        UpdateSubCategoryRequestDTO updateSubCategoryRequestDTO) {

        categoryService.updateSubCategory(updateSubCategoryRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "서브 카테고리 수정 성공", null));
    }

    @DeleteMapping("/sub")
    @Operation(summary = "서브 카테고리 삭제 API")
    public ResponseEntity<ResponseMessage<Object>> deleteSubCategory(@RequestBody
        DeleteSubCategoryRequestDTO deleteSubCategoryRequestDTO) {

        categoryService.deleteSubCategory(deleteSubCategoryRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "서브 카테고리 삭제 성공", null));
    }
    

    @PutMapping("/super")
    @Operation(summary = "상위 카테고리 수정 API")
    public ResponseEntity<ResponseMessage<Object>> updateSuperCategory(@RequestBody
        UpdateSuperCategoryRequestDTO updateSuperCategoryRequestDTO) {

        categoryService.updateSuperCategory(updateSuperCategoryRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "상위 카테고리 수정 성공", null));
    }

    @DeleteMapping("/super")
    @Operation(summary = "상위 카테고리 삭제 API")
    public ResponseEntity<ResponseMessage<Object>> deleteSuperCategory(@RequestBody
        DeleteSuperCategoryRequestDTO deleteSuperCategoryRequestDTO) {

        categoryService.deleteSuperCategory(deleteSuperCategoryRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "상위 카테고리 삭제 성공", null));
    }
}

