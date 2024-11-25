package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("ExchangeStatusHistoryRepositoryCommand")
public interface ExchangeStatusHistoryRepository extends JpaRepository<ExchangeStatusHistory, Integer> {

}
