package com.varc.brewnetapp.domain.purchase.query.service;

import com.varc.brewnetapp.domain.purchase.common.PageResponse;
import com.varc.brewnetapp.domain.purchase.query.dto.LetterOfPurchaseDTO;
import com.varc.brewnetapp.domain.purchase.query.dto.LetterOfPurchaseDetailDTO;

import java.util.List;

public interface PurchaseService {

    PageResponse<List<LetterOfPurchaseDTO>> selectLettersOfPurchase(Integer purchaseCode,
                                                                    String memberName,
                                                                    String correspondentName,
                                                                    String storageName,
                                                                    String startDate,
                                                                    String endDate,
                                                                    int pageNumber,
                                                                    int pageSize);

    LetterOfPurchaseDetailDTO selectOneLetterOfPurchase(int letterOfPurchaseCode);
}
