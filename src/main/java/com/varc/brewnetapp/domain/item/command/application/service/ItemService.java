package com.varc.brewnetapp.domain.item.command.application.service;

import com.varc.brewnetapp.domain.item.command.application.dto.MustBuyItemDTO;

public interface ItemService {
    void setMustBuyItem(
            int memberCode,
            Integer itemCode,
            MustBuyItemDTO mustBuyItemDTO
    );
}
