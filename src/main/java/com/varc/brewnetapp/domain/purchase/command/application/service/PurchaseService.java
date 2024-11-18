package com.varc.brewnetapp.domain.purchase.command.application.service;

import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseCreateDTO;

public interface PurchaseService {

    void createLetterOfPurchase(PurchaseCreateDTO newPurchase);
}
