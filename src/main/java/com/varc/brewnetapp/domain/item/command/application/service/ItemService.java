package com.varc.brewnetapp.domain.item.command.application.service;

import com.varc.brewnetapp.domain.item.command.application.dto.CreateItemRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.DeleteItemRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.MustBuyItemDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.UpdateItemRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ItemService {
    void setMustBuyItem(
            int memberCode,
            Integer itemCode,
            MustBuyItemDTO mustBuyItemDTO
    );

    void updateMustByItem(
            int memberCode,
            int itemCode,
            MustBuyItemDTO mustBuyItemDTO
    );

    void createItem(CreateItemRequestDTO createItemRequestDTO, MultipartFile image);

    void updateItem(UpdateItemRequestDTO updateItemRequestDTO, MultipartFile iamge);

    void deleteSubCategory(DeleteItemRequestDTO deleteItemRequestDTO);
}
