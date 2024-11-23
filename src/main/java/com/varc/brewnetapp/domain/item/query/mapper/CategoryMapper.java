package com.varc.brewnetapp.domain.item.query.mapper;

import com.varc.brewnetapp.domain.item.query.dto.SubCategoryDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    List<SubCategoryDTO> selectSubCategory();
}