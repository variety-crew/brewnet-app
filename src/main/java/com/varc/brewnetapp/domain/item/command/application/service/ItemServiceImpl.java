package com.varc.brewnetapp.domain.item.command.application.service;

import com.varc.brewnetapp.domain.item.command.application.dto.MustBuyItemDTO;
import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.Item;
import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.MandatoryPurchase;
import com.varc.brewnetapp.domain.item.command.domain.repository.ItemRepository;
import com.varc.brewnetapp.domain.item.command.domain.repository.MandatoryPurchaseRepository;
import com.varc.brewnetapp.exception.ItemNotFoundException;
import com.varc.brewnetapp.utility.time.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final MandatoryPurchaseRepository mandatoryPurchaseRepository;
    @Autowired
    public ItemServiceImpl(
            ItemRepository itemRepository,
            MandatoryPurchaseRepository mandatoryPurchaseRepository
    ) {
        this.itemRepository = itemRepository;
        this.mandatoryPurchaseRepository = mandatoryPurchaseRepository;
    }

    @Override
    public void setMustBuyItem(
            int memberCode,
            Integer itemCode,
            MustBuyItemDTO mustBuyItemDTO
    ) {
        Item targetItem = itemRepository.findItemByItemCodeAndActiveTrue(itemCode)
                .orElseThrow(() -> new ItemNotFoundException("아이템이 존재하지 않습니다."));

        mandatoryPurchaseRepository.save(
                MandatoryPurchase.builder()
                        .minQuantity(mustBuyItemDTO.getQuantity())
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .itemCode(itemCode)
                        .dueDate(Formatter.toLocalDateTime(mustBuyItemDTO.getDueDate()))
                        .satisfied(false)
                        .memberCode(memberCode)
                        .build()
        );


//     TODO: 유효한 아이템인지 확인
//      tbl_mandatory_purchase에 추가 시, satisfied => false
    }
}
