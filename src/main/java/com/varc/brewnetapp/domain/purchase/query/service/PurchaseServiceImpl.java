package com.varc.brewnetapp.domain.purchase.query.service;

import com.varc.brewnetapp.domain.purchase.common.SearchPurchaseCriteria;
import com.varc.brewnetapp.domain.purchase.query.dto.LetterOfPurchaseDTO;
import com.varc.brewnetapp.domain.purchase.query.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<LetterOfPurchaseDTO> selectLettersOfPurchase(SearchPurchaseCriteria criteria) {
        List<LetterOfPurchaseDTO> lettersOfPurchase = purchaseMapper.searchLettersOfPurchase(criteria);
        return lettersOfPurchase;
    }
}
