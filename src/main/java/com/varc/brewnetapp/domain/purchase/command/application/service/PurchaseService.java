package com.varc.brewnetapp.domain.purchase.command.application.service;

import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseRequestDTO;

public interface PurchaseService {

    void createLetterOfPurchase(PurchaseRequestDTO newPurchase);
}
