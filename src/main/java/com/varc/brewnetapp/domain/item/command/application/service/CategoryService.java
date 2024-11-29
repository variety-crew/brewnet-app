package com.varc.brewnetapp.domain.item.command.application.service;

import com.varc.brewnetapp.domain.item.command.application.dto.CreateCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.DeleteSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.DeleteSuperCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.UpdateSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.UpdateSuperCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.SubCategory;
import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.SuperCategory;
import com.varc.brewnetapp.domain.item.command.domain.repository.ItemRepository;
import com.varc.brewnetapp.domain.item.command.domain.repository.SubCategoryRepository;
import com.varc.brewnetapp.domain.item.command.domain.repository.SuperCategoryRepository;
import com.varc.brewnetapp.exception.DuplicateException;
import com.varc.brewnetapp.exception.InvalidDataException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "commandCategoryService")
public class CategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final SuperCategoryRepository superCategoryRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public CategoryService(SubCategoryRepository subCategoryRepository,
        SuperCategoryRepository superCategoryRepository, ItemRepository itemRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.superCategoryRepository = superCategoryRepository;
        this.itemRepository = itemRepository;
    }

    public void createCategory(CreateCategoryRequestDTO createCategoryRequestDTO) {

        if(createCategoryRequestDTO.getSuperCategoryCode() != null){

            superCategoryRepository.findById(createCategoryRequestDTO.getSuperCategoryCode())
                .orElseThrow(() -> new InvalidDataException("잘못된 상위 카테고리 코드를 입력했습니다"));

            SubCategory subCategory = subCategoryRepository.findByName(createCategoryRequestDTO.getCategoryName()).orElse(null);

            if(subCategory != null)
                throw new DuplicateException("이미 해당 이름의 하위 카테고리가 존재합니다");

            subCategoryRepository.save(SubCategory.builder()
                .createdAt(LocalDateTime.now())
                .active(true)
                .name(createCategoryRequestDTO.getCategoryName())
                .superCategoryCode(createCategoryRequestDTO.getSuperCategoryCode())
                .build());
        }
        else{
            SuperCategory superCategory = superCategoryRepository.findByName(createCategoryRequestDTO.getCategoryName()).orElse(null);

            if(superCategory != null)
                throw new DuplicateException("이미 해당 이름의 상위 카테고리가 존재합니다");
            
            superCategoryRepository.save(SuperCategory.builder()
                .createdAt(LocalDateTime.now())
                .active(true)
                .name(createCategoryRequestDTO.getCategoryName())
                .build());
        }
    }

    public void updateSubCategory(UpdateSubCategoryRequestDTO updateSubCategoryRequestDTO) {

        SubCategory existSubCategory = subCategoryRepository.findByName(
            updateSubCategoryRequestDTO.getSubCategoryName()).orElse(null);

        if(existSubCategory != null)
            throw new DuplicateException("이미 해당 이름의 하위 카테고리가 존재합니다");

        SubCategory subCategory = subCategoryRepository.findById(
            updateSubCategoryRequestDTO.getSubCategoryCode())
            .orElseThrow(() -> new InvalidDataException("코드에 해당하는 하위 카테고리가 없습니다"));

        subCategory.setName(updateSubCategoryRequestDTO.getSubCategoryName());

        subCategoryRepository.save(subCategory);

    }

    public void updateSuperCategory(UpdateSuperCategoryRequestDTO updateSuperCategoryRequestDTO) {
        SuperCategory existSuperCategory = superCategoryRepository.findByName(
            updateSuperCategoryRequestDTO.getSuperCategoryName()).orElse(null);

        if(existSuperCategory != null)
            throw new DuplicateException("이미 해당 이름의 상위 카테고리가 존재합니다");

        SuperCategory superCategory = superCategoryRepository.findById(
            updateSuperCategoryRequestDTO.getSuperCategoryCode())
            .orElseThrow(() -> new InvalidDataException("코드에 해당하는 상위 카테고리가 없습니다"));

        superCategory.setName(updateSuperCategoryRequestDTO.getSuperCategoryName());

        superCategoryRepository.save(superCategory);
    }

    public void deleteSubCategory(DeleteSubCategoryRequestDTO deleteSubCategoryRequestDTO) {

    }

    public void deleteSuperCategory(DeleteSuperCategoryRequestDTO deleteSuperCategoryRequestDTO) {
    }
}
