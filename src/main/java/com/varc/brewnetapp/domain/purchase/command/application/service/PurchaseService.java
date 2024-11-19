package com.varc.brewnetapp.domain.purchase.command.application.service;

import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseApprovalRequestDTO;
import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseRequestDTO;

public interface PurchaseService {

    void createLetterOfPurchase(PurchaseRequestDTO newPurchase);

    void cancelLetterOfPurchase(int letterOfPurchaseCode);

    void approveLetterOfPurchase(int letterOfPurchaseCode, PurchaseApprovalRequestDTO request);

    void rejectLetterOfPurchase(int letterOfPurchaseCode, PurchaseApprovalRequestDTO request);

    void changeInStockToAvailable(int itemCode, int purchaseCode);
}
