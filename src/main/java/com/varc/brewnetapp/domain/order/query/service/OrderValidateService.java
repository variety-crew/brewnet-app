package com.varc.brewnetapp.domain.order.query.service;

public interface OrderValidateService {
    boolean isOrderFromFranchise(int franchiseCode, int orderCode);

    boolean isOrderDrafted(Integer orderCode);
}
