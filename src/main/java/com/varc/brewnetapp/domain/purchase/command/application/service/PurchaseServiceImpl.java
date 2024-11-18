package com.varc.brewnetapp.domain.purchase.command.application.service;

import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseItemDTO;
import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseRequestDTO;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.*;
import com.varc.brewnetapp.domain.purchase.command.domain.repository.*;
import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import com.varc.brewnetapp.exception.DuplicateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("PurchaseServiceCommand")
public class PurchaseServiceImpl implements PurchaseService {

    private final ModelMapper modelMapper;
    private final LetterOfPurchaseRepository letterOfPurchaseRepository;
    private final PurchaseMemberRepository purchaseMemberRepository;
    private final PurchaseStatusHistoryRepository purchaseStatusHistoryRepository;
    private final StockRepository stockRepository;
    private final CorrespondentRepository correspondentRepository;
    private final StorageRepository storageRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public PurchaseServiceImpl(ModelMapper modelMapper,
                               LetterOfPurchaseRepository letterOfPurchaseRepository,
                               PurchaseMemberRepository purchaseMemberRepository,
                               PurchaseStatusHistoryRepository purchaseStatusHistoryRepository,
                               StockRepository stockRepository,
                               CorrespondentRepository correspondentRepository,
                               StorageRepository storageRepository,
                               ItemRepository itemRepository) {
        this.modelMapper = modelMapper;
        this.letterOfPurchaseRepository = letterOfPurchaseRepository;
        this.purchaseMemberRepository = purchaseMemberRepository;
        this.purchaseStatusHistoryRepository = purchaseStatusHistoryRepository;
        this.stockRepository = stockRepository;
        this.correspondentRepository = correspondentRepository;
        this.storageRepository = storageRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    @Override
    public void createLetterOfPurchase(PurchaseRequestDTO newPurchase) {
        LetterOfPurchase letterOfPurchase = modelMapper.map(newPurchase, LetterOfPurchase.class);

        PurchaseMember member = purchaseMemberRepository.findById(newPurchase.getMemberCode())
                .orElseThrow(() -> new DuplicateException("존재하지 않는 회원입니다."));

        Correspondent correspondent = correspondentRepository.findById(newPurchase.getCorrespondentCode())
                .orElseThrow(() -> new DuplicateException("존재하지 않는 거래처입니다."));

        Storage storage = storageRepository.findById(newPurchase.getStorageCode())
                .orElseThrow(() -> new DuplicateException("존재하지 않는 창고입니다."));

        letterOfPurchase.setCreatedAt(LocalDateTime.now());
        letterOfPurchase.setActive(true);
        letterOfPurchase.setApproved(IsApproved.UNCONFIRMED);
        letterOfPurchase.setMember(member);
        letterOfPurchase.setCorrespondent(correspondent);
        letterOfPurchase.setStorage(storage);

        int totalPrice = 0;
        List<PurchaseItemDTO> items = newPurchase.getItems();
        for (PurchaseItemDTO purchaseItem : items) {
            Item item = itemRepository.findById(purchaseItem.getItemCode())
                    .orElseThrow(() -> new DuplicateException("존재하지 않는 품목입니다."));

            totalPrice += (item.getPurchasePrice() * purchaseItem.getQuantity());
        }

        // 품목이 해당 거래처의 품목인지 체크 필요
        letterOfPurchase.setSumPrice(totalPrice);
        letterOfPurchaseRepository.save(letterOfPurchase);
        // 결재 관련 내용 추가 필요
    }
}
