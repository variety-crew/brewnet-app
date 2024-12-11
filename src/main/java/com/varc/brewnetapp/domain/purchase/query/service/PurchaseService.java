package com.varc.brewnetapp.domain.purchase.query.service;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import com.varc.brewnetapp.domain.purchase.common.PageResponse;
import com.varc.brewnetapp.domain.purchase.query.dto.*;

import java.util.List;

public interface PurchaseService {

    PageResponse<List<LetterOfPurchaseDTO>> selectLettersOfPurchase(String loginId,
                                                                    Integer purchaseCode,
                                                                    String memberName,
                                                                    String correspondentName,
                                                                    String storageName,
                                                                    IsApproved approved,
                                                                    String startDate,
                                                                    String endDate,
                                                                    int pageNumber,
                                                                    int pageSize);

    LetterOfPurchaseDetailDTO selectOneLetterOfPurchase(String loginId, int letterOfPurchaseCode);

    PurchaseApprovalLineDTO selectApprovalLineOfOnePurchase(String loginId, int letterOfPurchaseCode);

    PageResponse<List<ApprovedPurchaseItemDTO>> selectApprovedPurchaseItems(String loginId,
                                                                            String itemUniqueCode,
                                                                            String itemName,
                                                                            String correspondentName,
                                                                            String storageName,
                                                                            String startDate,
                                                                            String endDate,
                                                                            int pageNumber,
                                                                            int pageSize);

    PageResponse<List<ApprovedPurchaseItemDTO>> selectApprovedPurchaseItemUncheck(String loginId,
                                                                                  String itemUniqueCode,
                                                                                  String itemName,
                                                                                  String correspondentName,
                                                                                  String storageName,
                                                                                  String startDate,
                                                                                  String endDate,
                                                                                  int pageNumber,
                                                                                  int pageSize);
}
