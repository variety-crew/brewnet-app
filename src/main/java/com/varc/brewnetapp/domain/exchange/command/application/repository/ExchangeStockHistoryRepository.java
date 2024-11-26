package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ExchangeStockHistoryRepositoryCommand")
public interface ExchangeStockHistoryRepository extends JpaRepository<ExchangeStockHistory, Integer> {
}
