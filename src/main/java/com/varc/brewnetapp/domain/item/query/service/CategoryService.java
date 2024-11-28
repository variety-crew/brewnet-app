package com.varc.brewnetapp.domain.item.query.service;

import com.varc.brewnetapp.domain.item.query.dto.CategoryDTO;
import com.varc.brewnetapp.domain.item.query.dto.SubCategoryDTO;
import com.varc.brewnetapp.domain.item.query.mapper.CategoryMapper;
import com.varc.brewnetapp.exception.EmptyDataException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "queryCategoryService")
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public List<SubCategoryDTO> findSubCategory() {

        List<SubCategoryDTO> subCategoryList = categoryMapper.selectSubCategory();

        if(subCategoryList == null || subCategoryList.isEmpty())
            throw new EmptyDataException("서브 카테고리가 없습니다");
        return subCategoryList;

    }

    public List<CategoryDTO> findCategory() {

        List<CategoryDTO> CategoryList = categoryMapper.selectCategory();

        if(CategoryList == null || CategoryList.isEmpty())
            throw new EmptyDataException("카테고리가 없습니다");
        return CategoryList;
    }
}
