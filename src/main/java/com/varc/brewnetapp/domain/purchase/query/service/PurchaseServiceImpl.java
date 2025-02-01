package com.varc.brewnetapp.domain.purchase.query.service;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import com.varc.brewnetapp.domain.purchase.common.PageResponse;
import com.varc.brewnetapp.domain.purchase.common.SearchPurchaseCriteria;
import com.varc.brewnetapp.domain.purchase.common.SearchPurchaseItemCriteria;
import com.varc.brewnetapp.domain.purchase.query.dto.*;
import com.varc.brewnetapp.domain.purchase.query.mapper.PurchaseMapper;
import com.varc.brewnetapp.exception.InvalidConditionException;
import com.varc.brewnetapp.exception.PurchaseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("PurchaseServiceQuery")
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseMapper purchaseMapper;

    @Autowired
    public PurchaseServiceImpl(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<List<LetterOfPurchaseDTO>> selectLettersOfPurchase(String loginId,
                                                                           Integer purchaseCode,
                                                                           String memberName,
                                                                           String correspondentName,
                                                                           String storageName,
                                                                           IsApproved approved,
                                                                           String startDate,
                                                                           String endDate,
                                                                           int pageNumber,
                                                                           int pageSize) {

        SearchPurchaseCriteria criteria = new SearchPurchaseCriteria();
        criteria.setPurchaseCode(purchaseCode);
        criteria.setMemberName(memberName);
        criteria.setCorrespondentName(correspondentName);
        criteria.setStorageName(storageName);
        criteria.setApproved(approved);
        criteria.setStartDate(startDate);
        criteria.setEndDate(endDate);
        criteria.setPageNumber(pageNumber);
        criteria.setPageSize(pageSize);

        int offset = (pageNumber - 1) * pageSize;
        criteria.setOffset(offset);

        if (criteria.getStartDate() != null && criteria.getEndDate() == null) {
            throw new InvalidConditionException("종료일을 입력해 주세요.");
        } else if (criteria.getStartDate() == null && criteria.getEndDate() != null) {
            throw new InvalidConditionException("시작일을 입력해 주세요.");
        }

        List<LetterOfPurchaseDTO> lettersOfPurchase = purchaseMapper.searchLettersOfPurchase(criteria);
        int totalCount = purchaseMapper.getTotalPurchaseCount(criteria);
        PageResponse<List<LetterOfPurchaseDTO>> response = new PageResponse<>(
                                                            lettersOfPurchase, pageNumber, pageSize, totalCount);

        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public LetterOfPurchaseDetailDTO selectOneLetterOfPurchase(String loginId, int letterOfPurchaseCode) {

        LetterOfPurchaseDetailDTO letterOfPurchase = purchaseMapper
                                                    .selectLetterOfPurchaseByPurchaseCode(letterOfPurchaseCode);

        if (letterOfPurchase == null) throw new PurchaseNotFoundException("존재하지 않는 발주 내역 입니다.");
        if (!letterOfPurchase.isActive()) throw new PurchaseNotFoundException("삭제된 발주 내역 입니다.");

        return letterOfPurchase;
    }

    @Transactional(readOnly = true)
    @Override
    public PurchaseApprovalLineDTO selectApprovalLineOfOnePurchase(String loginId, int letterOfPurchaseCode) {

        LetterOfPurchaseDetailDTO purchase = purchaseMapper
                                            .selectLetterOfPurchaseByPurchaseCode(letterOfPurchaseCode);

        if (purchase == null) throw new PurchaseNotFoundException("존재하지 않는 발주 내역 입니다.");
        if (!purchase.isActive()) throw new PurchaseNotFoundException("삭제된 발주 내역 입니다.");

        PurchaseApprovalLineDTO approvalOfPurchase = purchaseMapper
                                                    .selectApprovalLineByPurchaseCode(letterOfPurchaseCode);

        return approvalOfPurchase;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<List<ApprovedPurchaseItemDTO>> selectApprovedPurchaseItems(String loginId,
                                                                                   String itemUniqueCode,
                                                                                   String itemName,
                                                                                   String correspondentName,
                                                                                   String storageName,
                                                                                   String startDate,
                                                                                   String endDate,
                                                                                   int pageNumber,
                                                                                   int pageSize) {

        SearchPurchaseItemCriteria criteria = new SearchPurchaseItemCriteria();
        criteria.setItemUniqueCode(itemUniqueCode);
        criteria.setItemName(itemName);
        criteria.setCorrespondentName(correspondentName);
        criteria.setStorageName(storageName);
        criteria.setStartDate(startDate);
        criteria.setEndDate(endDate);
        criteria.setPageNumber(pageNumber);
        criteria.setPageSize(pageSize);

        int offset = (pageNumber - 1) * pageSize;
        criteria.setOffset(offset);

        if (criteria.getStartDate() != null && criteria.getEndDate() == null) {
            throw new InvalidConditionException("종료일을 입력해 주세요.");
        } else if (criteria.getStartDate() == null && criteria.getEndDate() != null) {
            throw new InvalidConditionException("시작일을 입력해 주세요.");
        }

        List<ApprovedPurchaseItemDTO> purchaseItems = purchaseMapper.selectApprovedPurchaseItemTotal(criteria);
        int totalCount = purchaseMapper.getTotalApprovedPurchaseItemCount(criteria);
        PageResponse<List<ApprovedPurchaseItemDTO>> response = new PageResponse<>(
                                                                purchaseItems, pageNumber, pageSize, totalCount);

        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<List<ApprovedPurchaseItemDTO>> selectApprovedPurchaseItemUncheck(String loginId,
                                                                                         String itemUniqueCode,
                                                                                         String itemName,
                                                                                         String correspondentName,
                                                                                         String storageName,
                                                                                         String startDate,
                                                                                         String endDate,
                                                                                         int pageNumber,
                                                                                         int pageSize) {

        SearchPurchaseItemCriteria criteria = new SearchPurchaseItemCriteria();
        criteria.setItemUniqueCode(itemUniqueCode);
        criteria.setItemName(itemName);
        criteria.setCorrespondentName(correspondentName);
        criteria.setStorageName(storageName);
        criteria.setStartDate(startDate);
        criteria.setEndDate(endDate);
        criteria.setPageNumber(pageNumber);
        criteria.setPageSize(pageSize);

        int offset = (pageNumber - 1) * pageSize;
        criteria.setOffset(offset);

        if (criteria.getStartDate() != null && criteria.getEndDate() == null) {
            throw new InvalidConditionException("종료일을 입력해 주세요.");
        } else if (criteria.getStartDate() == null && criteria.getEndDate() != null) {
            throw new InvalidConditionException("시작일을 입력해 주세요.");
        }

        List<ApprovedPurchaseItemDTO> purchaseItems = purchaseMapper.selectApprovedPurchaseItemUncheck(criteria);
        int totalCount = purchaseMapper.getApprovedPurchaseItemUncheckCount(criteria);
        PageResponse<List<ApprovedPurchaseItemDTO>> response = new PageResponse<>(
                                                                purchaseItems, pageNumber, pageSize, totalCount);

        return response;
    }
}
