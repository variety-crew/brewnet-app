package com.varc.brewnetapp.domain.item.query.service;

import com.varc.brewnetapp.domain.item.query.dto.SubCategoryDTO;
import com.varc.brewnetapp.domain.item.query.mapper.CategoryMapper;
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

        return categoryMapper.selectSubCategory();
    }
}
