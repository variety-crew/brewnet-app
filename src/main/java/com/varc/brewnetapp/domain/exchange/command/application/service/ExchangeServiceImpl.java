package com.varc.brewnetapp.domain.exchange.command.application.service;

import com.varc.brewnetapp.domain.exchange.command.application.repository.*;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.Exchange;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItem;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItemCode;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeStatusHistory;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrder;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqItemVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqVO;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeApproval;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
import com.varc.brewnetapp.exception.ExchangeNotFoundException;
import com.varc.brewnetapp.exception.InvalidStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service("ExchangeServiceCommand")
@RequiredArgsConstructor
@Slf4j
public class ExchangeServiceImpl implements ExchangeService{

    private final ExchangeRepository exchangeRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ExchangeItemRepository exchangeItemRepository;
    private final ExchangeStatusHistoryRepository exchangeStatusHistoryRepository;
    private final com.varc.brewnetapp.domain.exchange.query.service.ExchangeServiceImpl exchangeServiceQuery;

    @Override
    @Transactional
    public void createExchange(ExchangeReqVO exchangeReqVO) {

        // 1. 교환 저장
        Exchange exchange = new Exchange();
        ExOrder order = orderRepository.findById(exchangeReqVO.getOrderCode()).orElse(null);

        exchange.setComment(null);          // 첨언 null
        exchange.setCreatedAt(String.valueOf(LocalDateTime.now()));
        exchange.setActive(true);
        exchange.setReason(exchangeReqVO.getReason());
        exchange.setExplanation(exchangeReqVO.getExplanation());
        exchange.setApproved(ExchangeApproval.UNCONFIRMED);
        exchange.setOrder(order);
        exchange.setMemberCode(null);       // 교환 기안자 null
        exchange.setDelivery(null);         // 배송기사 null
        exchange.setDrafterApproved(null);  // 기안자의 교환 승인 여부 null
        exchange.setSumPrice(exchangeReqVO.getSumPrice());

        exchangeRepository.save(exchange);


        // 2. 교환별상품 저장
        int exchangeCode = exchange.getExchangeCode();

        for (ExchangeReqItemVO reqItem: exchangeReqVO.getExchangeItemList()) {

            // 복합키 객체 생성
            ExchangeItemCode exchangeItemCode = new ExchangeItemCode();
            exchangeItemCode.setExchangeCode(exchangeCode);                 // 교환 코드 설정
            exchangeItemCode.setItemCode(reqItem.getItemCode());            // 상품 코드 설정

            // ExchangeItem 객체 생성
            ExchangeItem exchangeItem = new ExchangeItem();
            exchangeItem.setExchangeItemCode(exchangeItemCode);  // 복합키 설정
            exchangeItem.setQuantity(reqItem.getQuantity());

            exchangeItemRepository.save(exchangeItem);
        }


        // 3. 교환상태이력 저장
        ExchangeStatusHistory exchangeStatusHistory = new ExchangeStatusHistory();
        exchangeStatusHistory.setStatus(ExchangeStatus.REQUESTED);
        exchangeStatusHistory.setCreatedAt(String.valueOf(LocalDateTime.now()));
        exchangeStatusHistory.setActive(true);
        exchangeStatusHistory.setExchange(exchange);

        exchangeStatusHistoryRepository.save(exchangeStatusHistory);


        // 4. 교환품목사진 저장

    }

    @Transactional
    public boolean cancelExchange(Integer exchangeCode) {

        Exchange exchange = exchangeRepository.findById(exchangeCode)
                .orElseThrow(() -> new ExchangeNotFoundException("교환 코드가 존재하지 않습니다."));

        ExchangeStatus exchangeStatus = exchangeServiceQuery.findExchangeLatestStatus(exchangeCode);

        // status가 REQUESTED인 경우에만 취소 가능
        if (exchangeStatus == ExchangeStatus.REQUESTED) {

            ExchangeStatusHistory exchangeStatusHistory = new ExchangeStatusHistory();
            exchangeStatusHistory.setStatus(ExchangeStatus.CANCELED);
            exchangeStatusHistory.setCreatedAt(String.valueOf(LocalDateTime.now()));
            exchangeStatusHistory.setActive(true);
            exchangeStatusHistory.setExchange(exchange);

            exchangeStatusHistoryRepository.save(exchangeStatusHistory);

            return true;
        } else {
            throw new InvalidStatusException("교환신청 취소가 불가능합니다.");
        }
    }
}
