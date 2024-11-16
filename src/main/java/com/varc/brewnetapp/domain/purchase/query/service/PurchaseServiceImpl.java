package com.varc.brewnetapp.domain.purchase.query.service;

import com.varc.brewnetapp.domain.purchase.common.PageResponse;
import com.varc.brewnetapp.domain.purchase.common.SearchPurchaseCriteria;
import com.varc.brewnetapp.domain.purchase.query.dto.LetterOfPurchaseDTO;
import com.varc.brewnetapp.domain.purchase.query.dto.LetterOfPurchaseDetailDTO;
import com.varc.brewnetapp.domain.purchase.query.mapper.PurchaseMapper;
import com.varc.brewnetapp.exception.DuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseMapper purchaseMapper;

    @Autowired
    public PurchaseServiceImpl(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<List<LetterOfPurchaseDTO>> selectLettersOfPurchase(Integer purchaseCode,
                                                                           String memberName,
                                                                           String correspondentName,
                                                                           String storageName,
                                                                           String startDate,
                                                                           String endDate,
                                                                           int pageNumber,
                                                                           int pageSize) {

        SearchPurchaseCriteria criteria = new SearchPurchaseCriteria();
        criteria.setPurchaseCode(purchaseCode);
        criteria.setMemberName(memberName);
        criteria.setCorrespondentName(correspondentName);
        criteria.setStorageName(storageName);
        criteria.setStartDate(startDate);
        criteria.setEndDate(endDate);
        criteria.setPageNumber(pageNumber);
        criteria.setPageSize(pageSize);

        int offset = (pageNumber - 1) * pageSize;
        criteria.setOffset(offset);

        if (criteria.getStartDate() != null && criteria.getEndDate() == null) {
            throw new DuplicateException("종료일을 입력해 주세요.");
        } else if (criteria.getStartDate() == null && criteria.getEndDate() != null) {
            throw new DuplicateException("시작일을 입력해 주세요.");
        }

        List<LetterOfPurchaseDTO> lettersOfPurchase = purchaseMapper.searchLettersOfPurchase(criteria);
        int totalCount = purchaseMapper.getTotalPurchaseCount(criteria);
        PageResponse<List<LetterOfPurchaseDTO>> response = new PageResponse<>(
                                                            lettersOfPurchase, pageNumber, pageSize, totalCount);

        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public LetterOfPurchaseDetailDTO selectOneLetterOfPurchase(int letterOfPurchaseCode) {

        LetterOfPurchaseDetailDTO letterOfPurchase = purchaseMapper
                                                    .selectLetterOfPurchaseByPurchaseCode(letterOfPurchaseCode);

        if (letterOfPurchase == null) throw new DuplicateException("존재하지 않는 발주 내역 입니다.");
        if (!letterOfPurchase.getActive()) throw new DuplicateException("삭제된 발주 내역 입니다.");

        return letterOfPurchase;
    }
}
