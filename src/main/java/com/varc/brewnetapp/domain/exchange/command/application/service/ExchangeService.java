package com.varc.brewnetapp.domain.exchange.command.application.service;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeDrafterApproveReqVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeManagerApproveReqVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExchangeService {
    Integer franCreateExchange(String loginId, ExchangeReqVO exchangeReqVO, List<MultipartFile> exchangeImageList);

    void franCancelExchange(String loginId, Integer exchangeCode);

    void drafterExchange(String loginId, int exchangeCode, ExchangeDrafterApproveReqVO exchangeApproveReqVO);

    void managerExchange(String loginId, int exchangeCode, ExchangeManagerApproveReqVO exchangeApproveReqVO);

    void completeExchange(String loginId, int exchangeStockHistoryCode);

    void cancelApprove(String loginId, int exchangeCode);
}
