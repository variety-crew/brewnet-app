package com.varc.brewnetapp.domain.item.query.mapper;

import com.varc.brewnetapp.domain.item.query.dto.CategoryDTO;
import com.varc.brewnetapp.domain.item.query.dto.SubCategoryDTO;
import com.varc.brewnetapp.domain.item.query.dto.SuperCategoryDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    List<SubCategoryDTO> selectSubCategory();

    List<CategoryDTO> selectCategory();

    List<SuperCategoryDTO> selectSuperCategory();

    List<CategoryDTO> selectSubCategoryWhereSuperCategoryCode(Integer superCategoryCode);
}
