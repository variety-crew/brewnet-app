package com.varc.brewnetapp.domain.purchase.command.application.service;

import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseItemDTO;
import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseRequestDTO;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.*;
import com.varc.brewnetapp.domain.purchase.command.domain.repository.*;
import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import com.varc.brewnetapp.domain.purchase.common.Status;
import com.varc.brewnetapp.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service("PurchaseServiceCommand")
public class PurchaseServiceImpl implements PurchaseService {

    private final ModelMapper modelMapper;
    private final LetterOfPurchaseRepository letterOfPurchaseRepository;
    private final PurchaseMemberRepository purchaseMemberRepository;
    private final PurchaseStatusHistoryRepository purchaseStatusHistoryRepository;
    private final StockRepository stockRepository;
    private final CorrespondentRepository correspondentRepository;
    private final StorageRepository storageRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final CorrespondentItemRepository correspondentItemRepository;
    private final LetterOfPurchaseItemRepository letterOfPurchaseItemRepository;
    private final PurchaseApprovalRepository purchaseApprovalRepository;
    private final LetterOfPurchaseApproverRepository letterOfPurchaseApproverRepository;
    private final PurchasePositionRepository purchasePositionRepository;

    @Autowired
    public PurchaseServiceImpl(ModelMapper modelMapper,
                               LetterOfPurchaseRepository letterOfPurchaseRepository,
                               PurchaseMemberRepository purchaseMemberRepository,
                               PurchaseStatusHistoryRepository purchaseStatusHistoryRepository,
                               StockRepository stockRepository,
                               CorrespondentRepository correspondentRepository,
                               StorageRepository storageRepository,
                               PurchaseItemRepository purchaseItemRepository,
                               CorrespondentItemRepository correspondentItemRepository,
                               LetterOfPurchaseItemRepository letterOfPurchaseItemRepository,
                               PurchaseApprovalRepository purchaseApprovalRepository,
                               LetterOfPurchaseApproverRepository letterOfPurchaseApproverRepository,
                               PurchasePositionRepository purchasePositionRepository) {
        this.modelMapper = modelMapper;
        this.letterOfPurchaseRepository = letterOfPurchaseRepository;
        this.purchaseMemberRepository = purchaseMemberRepository;
        this.purchaseStatusHistoryRepository = purchaseStatusHistoryRepository;
        this.stockRepository = stockRepository;
        this.correspondentRepository = correspondentRepository;
        this.storageRepository = storageRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.correspondentItemRepository = correspondentItemRepository;
        this.letterOfPurchaseItemRepository = letterOfPurchaseItemRepository;
        this.purchaseApprovalRepository = purchaseApprovalRepository;
        this.letterOfPurchaseApproverRepository = letterOfPurchaseApproverRepository;
        this.purchasePositionRepository = purchasePositionRepository;
    }

    @Transactional
    @Override
    public void createLetterOfPurchase(PurchaseRequestDTO newPurchase) {
        LetterOfPurchase letterOfPurchase = modelMapper.map(newPurchase, LetterOfPurchase.class);

        PurchaseMember member = purchaseMemberRepository.findById(newPurchase.getMemberCode())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        Correspondent correspondent = correspondentRepository.findById(newPurchase.getCorrespondentCode())
                .orElseThrow(() -> new CorrespondentNotFoundException("존재하지 않는 거래처입니다."));

        Storage storage = storageRepository.findById(newPurchase.getStorageCode())
                .orElseThrow(() -> new StorageNotFoundException("존재하지 않는 창고입니다."));

        letterOfPurchase.setCreatedAt(LocalDateTime.now());
        letterOfPurchase.setActive(true);
        letterOfPurchase.setApproved(IsApproved.UNCONFIRMED);
        letterOfPurchase.setMember(member);
        letterOfPurchase.setCorrespondent(correspondent);
        letterOfPurchase.setStorage(storage);
        letterOfPurchase.setComment(newPurchase.getComment());
        letterOfPurchase.setSumPrice(0);

        // 구매품의서의 총 발주금액을 0으로 하여 일단 저장
        LetterOfPurchase savedPurchase = letterOfPurchaseRepository.save(letterOfPurchase);
        int totalPrice = 0;
        List<PurchaseItemDTO> items = newPurchase.getItems();

        for (PurchaseItemDTO purchaseItem : items) {
            PurchaseItem item = purchaseItemRepository.findById(purchaseItem.getItemCode())
                    .orElseThrow(() -> new ItemNotFoundException("존재하지 않는 품목입니다."));

            // 선택한 품목이 선택한 거래처의 품목인지 체크
            if (!correspondentItemRepository.existsByCorrespondentCodeAndItemCodeAndActiveTrue(
                                            newPurchase.getCorrespondentCode(), purchaseItem.getItemCode())) {
                throw new ItemNotFoundException("해당 거래처에서 취급하는 품목이 아닙니다.");
            }

            // 품목별 총 공급가액 누적 합산
            totalPrice += (item.getPurchasePrice() * purchaseItem.getQuantity());

            LetterOfPurchaseItem letterOfPurchaseItem = new LetterOfPurchaseItem(
                    item.getItemCode(), savedPurchase.getLetterOfPurchaseCode(), purchaseItem.getQuantity());

            // 구매품의서별 상품 저장
            letterOfPurchaseItemRepository.save(letterOfPurchaseItem);
        }

        // 총 발주금액 저장
        savedPurchase.setSumPrice(totalPrice);

        // 해당 구매품의서의 결재 라인 및 결재자 설정
        PurchaseApproval approvalLine = purchaseApprovalRepository
                                        .findByKindAndActiveTrue(newPurchase.getKind());
        PurchasePosition position = purchasePositionRepository
                                    .findById(approvalLine.getPurchasePosition().getPositionCode())
                                    .orElseThrow(() -> new PositionNotFoundException("존재하지 않는 직급입니다."));
        PurchaseMember approver = purchaseMemberRepository.findByPurchasePositionAndActiveTrue(position);
        LetterOfPurchaseApprover purchaseApprover = new LetterOfPurchaseApprover();
        purchaseApprover.setMemberCode(approver.getMemberCode());
        purchaseApprover.setLetterOfPurchaseCode(savedPurchase.getLetterOfPurchaseCode());
        purchaseApprover.setApproved(IsApproved.UNCONFIRMED);
        purchaseApprover.setApprovedAt(null);
        purchaseApprover.setActive(true);
        purchaseApprover.setComment(null);
        letterOfPurchaseApproverRepository.save(purchaseApprover);

        // 해당 구매품의서의 상태 이력에 결재 요청 상태 추가
        PurchaseStatusHistory purchaseStatusHistory = new PurchaseStatusHistory();
        purchaseStatusHistory.setStatus(Status.REQUESTED);
        purchaseStatusHistory.setCreatedAt(LocalDateTime.now());
        purchaseStatusHistory.setActive(true);
        purchaseStatusHistory.setLetterOfPurchase(savedPurchase);
        purchaseStatusHistoryRepository.save(purchaseStatusHistory);
    }

    @Transactional
    @Override
    public void cancelLetterOfPurchase(int letterOfPurchaseCode) {
        LetterOfPurchase purchase = letterOfPurchaseRepository.findById(letterOfPurchaseCode)
                                    .orElseThrow(() -> new PurchaseNotFoundException("존재하지 않는 구매품의서입니다."));

        // 결재 처리 전까지만 결재 요청 취소 가능
        if (!purchase.getApproved().equals(IsApproved.UNCONFIRMED)) {
            throw new InvalidDataException("이미 결재 처리가 완료된 구매품의서입니다.");
        }

        // 해당 구매품의서의 상태 이력에 결재 취소 상태 추가
        PurchaseStatusHistory history = new PurchaseStatusHistory();
        history.setLetterOfPurchase(purchase);
        history.setStatus(Status.CANCELED);
        history.setCreatedAt(LocalDateTime.now());
        history.setActive(true);
        purchaseStatusHistoryRepository.save(history);
    }
}
