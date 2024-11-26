package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItem;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItemStatus;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItemStatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExchangeItemStatusRepositoryCommand")
public interface ExchangeItemStatusRepository extends JpaRepository<ExchangeItemStatus, ExchangeItemStatusCode> {
    List<ExchangeItemStatus> findByExchangeItemStatusCode_ExchangeStockHistoryCode(int exchangeStockHistoryCode);
}
