package com.varc.brewnetapp.domain.item.query.service;

import com.varc.brewnetapp.domain.item.query.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    Page<ItemDTO> findItemList(Pageable page, String itemName, String itemCode, String sort, String categoryCode, String correspondentCode);
    int findItemSellingPriceByItemCode(int itemCode);
}
