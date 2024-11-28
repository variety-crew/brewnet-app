package com.varc.brewnetapp.domain.item.command.application.service;

import com.varc.brewnetapp.domain.item.command.application.dto.createSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.deleteSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.updateSubCategoryRequestDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service(value = "commandCategoryService")
public class CategoryService {

    public List<createSubCategoryRequestDTO> createSubCategory() {
        return null;
    }

    public List<updateSubCategoryRequestDTO> updateCategory() {
        return null;
    }

    public List<deleteSubCategoryRequestDTO> deleteCategory() {
        return null;
    }
}
