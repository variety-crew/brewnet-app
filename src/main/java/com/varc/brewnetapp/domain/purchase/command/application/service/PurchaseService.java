package com.varc.brewnetapp.domain.purchase.command.application.service;

import com.varc.brewnetapp.domain.purchase.command.application.dto.*;

public interface PurchaseService {

    void createLetterOfPurchase(String loginId, PurchaseRequestDTO newPurchase);

    void cancelLetterOfPurchase(String loginId, int letterOfPurchaseCode);

    void approveLetterOfPurchase(String loginId, int letterOfPurchaseCode, PurchaseApprovalRequestDTO request);

    void rejectLetterOfPurchase(String loginId, int letterOfPurchaseCode, PurchaseApprovalRequestDTO request);

    void changeInStockToAvailable(String loginId, int itemCode, int purchaseCode);

    PurchasePrintResponseDTO exportPurchasePrint(String loginId,
                                                 int letterOfPurchaseCode,
                                                 ExportPurchasePrintRequestDTO printRequest);

    PurchasePrintResponseDTO takeInHousePurchasePrint(String loginId, int letterOfPurchaseCode);

    void sendLetterOfPurchase(String loginId, int letterOfPurchaseCode);
}
