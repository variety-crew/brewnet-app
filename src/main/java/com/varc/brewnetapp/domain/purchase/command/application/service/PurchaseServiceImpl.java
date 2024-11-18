package com.varc.brewnetapp.domain.purchase.command.application.service;

import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseCreateDTO;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.LetterOfPurchase;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseMember;
import com.varc.brewnetapp.domain.purchase.command.domain.repository.*;
import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import com.varc.brewnetapp.exception.DuplicateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("PurchaseServiceCommand")
public class PurchaseServiceImpl implements PurchaseService {

    private final ModelMapper modelMapper;
    private final LetterOfPurchaseRepository letterOfPurchaseRepository;
    private final PurchaseMemberRepository purchaseMemberRepository;
    private final PurchaseStatusHistoryRepository purchaseStatusHistoryRepository;
    private final StockRepository stockRepository;
    private final CorrespondentRepository correspondentRepository;
    private final StorageRepository storageRepository;

    @Autowired
    public PurchaseServiceImpl(ModelMapper modelMapper,
                               LetterOfPurchaseRepository letterOfPurchaseRepository,
                               PurchaseMemberRepository purchaseMemberRepository,
                               PurchaseStatusHistoryRepository purchaseStatusHistoryRepository,
                               StockRepository stockRepository,
                               CorrespondentRepository correspondentRepository,
                               StorageRepository storageRepository) {
        this.modelMapper = modelMapper;
        this.letterOfPurchaseRepository = letterOfPurchaseRepository;
        this.purchaseMemberRepository = purchaseMemberRepository;
        this.purchaseStatusHistoryRepository = purchaseStatusHistoryRepository;
        this.stockRepository = stockRepository;
        this.correspondentRepository = correspondentRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    @Override
    public void createLetterOfPurchase(PurchaseCreateDTO newPurchase) {
        LetterOfPurchase letterOfPurchase = modelMapper.map(newPurchase, LetterOfPurchase.class);
        PurchaseMember member = purchaseMemberRepository.findById(newPurchase.getMemberCode())
                .orElseThrow(() -> new DuplicateException("존재하지 않는 회원입니다."));

        letterOfPurchase.setCreatedAt(LocalDateTime.now());
        letterOfPurchase.setActive(true);
        letterOfPurchase.setApproved(IsApproved.UNCONFIRMED);
        letterOfPurchase.setMember(member);

    }
}
