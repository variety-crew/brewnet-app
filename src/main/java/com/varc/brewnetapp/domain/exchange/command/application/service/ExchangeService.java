package com.varc.brewnetapp.domain.exchange.command.application.service;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeDrafterApproveReqVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeManagerApproveReqVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqVO;

public interface ExchangeService {
    void createExchange(String loginId, ExchangeReqVO exchangeReqVO);

    boolean cancelExchange(String loginId, Integer exchangeCode);

    void drafterExchange(String loginId, int exchangeCode, ExchangeDrafterApproveReqVO exchangeApproveReqVO);

    void managerExchange(String loginId, int exchangeCode, ExchangeManagerApproveReqVO exchangeApproveReqVO);
}
