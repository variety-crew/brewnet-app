package com.varc.brewnetapp.domain.item.command.application.service;

import com.varc.brewnetapp.domain.item.command.application.dto.MustBuyItemDTO;
import com.varc.brewnetapp.domain.item.command.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(
            ItemRepository itemRepository
    ) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void setMustBuyItem(
            int memberCode,
            Integer itemCode,
            MustBuyItemDTO mustBuyItemDTO
    ) {


//     TODO: 유효한 아이템인지 확인
//      tbl_mandatory_purchase에 추가 시, satisfied => false
    }
}
