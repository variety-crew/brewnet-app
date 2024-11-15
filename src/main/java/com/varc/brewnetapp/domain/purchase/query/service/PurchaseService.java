package com.varc.brewnetapp.domain.purchase.query.service;

import com.varc.brewnetapp.domain.purchase.common.SearchPurchaseCriteria;
import com.varc.brewnetapp.domain.purchase.query.dto.LetterOfPurchaseDTO;

import java.util.List;

public interface PurchaseService {

    List<LetterOfPurchaseDTO> selectLettersOfPurchase(SearchPurchaseCriteria criteria);
}
