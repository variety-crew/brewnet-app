package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExchangeStatusHistoryRepository extends JpaRepository<ExchangeStatusHistory, Integer> {

}
