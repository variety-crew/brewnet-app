package com.varc.brewnetapp.domain.item.command.application.service;

import com.varc.brewnetapp.domain.item.command.application.dto.MustBuyItemDTO;
import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.Item;
import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.MandatoryPurchase;
import com.varc.brewnetapp.domain.item.command.domain.repository.ItemRepository;
import com.varc.brewnetapp.domain.item.command.domain.repository.MandatoryPurchaseRepository;
import com.varc.brewnetapp.exception.InvalidItemException;
import com.varc.brewnetapp.exception.ItemNotFoundException;
import com.varc.brewnetapp.exception.MandatoryPurchaseNotFound;
import com.varc.brewnetapp.exception.MustBuyItemAlreadySet;
import com.varc.brewnetapp.utility.time.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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
        itemRepository.findItemByItemCodeAndActiveTrue(itemCode)
                .orElseThrow(() -> new InvalidItemException("유효하지 않은 품목입니다."));

        if (mandatoryPurchaseRepository.findMandatoryPurchaseByItemCodeAndActiveTrue(itemCode).isPresent()) {
            throw new MustBuyItemAlreadySet("이미 해당 품목에 대한 필수 구매 정보가 지정되어있습니다.");
        } else {
            mandatoryPurchaseRepository.save(
                    MandatoryPurchase.builder()
                            .minQuantity(mustBuyItemDTO.getQuantity())
                            .createdAt(LocalDateTime.now())
                            .active(true)
                            .itemCode(itemCode)
                            .dueDate(Formatter.toLocalDateTime(mustBuyItemDTO.getDueDate()))
                            .memberCode(memberCode)
                            .build()
            );
        }
    }

    @Override
    @Transactional
    public void updateMustByItem(
            int memberCode,
            int itemCode,
            MustBuyItemDTO mustBuyItemDTO
    ) {
        itemRepository.findItemByItemCodeAndActiveTrue(itemCode)
                .orElseThrow(() -> new InvalidItemException("유효하지 않은 품목입니다."));
        MandatoryPurchase targetMandatoryPurchase = mandatoryPurchaseRepository.findMandatoryPurchaseByItemCodeAndActiveTrue(itemCode)
                .orElseThrow(() -> new MandatoryPurchaseNotFound("해당 품목에 대한 필수 구매 설정이 되어있지 않습니다."));

        mandatoryPurchaseRepository.save(
                MandatoryPurchase.builder()
                        .mandatoryPurchaseCode(targetMandatoryPurchase.getMandatoryPurchaseCode())
                        .minQuantity(mustBuyItemDTO.getQuantity())
                        .createdAt(LocalDateTime.now())
                        .active(targetMandatoryPurchase.isActive())
                        .itemCode(itemCode)
                        .dueDate(Formatter.toLocalDateTime(mustBuyItemDTO.getDueDate()))
                        .memberCode(memberCode)
                        .build()
        );
    }
}
