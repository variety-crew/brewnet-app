package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeStatusHistory;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExchangeStatusHistoryRepository extends JpaRepository<ExchangeStatusHistory, Integer> {
    @Query(value = "SELECT B.status FROM tbl_exchange_status_history B " +
            "JOIN (SELECT exchange_code, MAX(created_at) AS max_created_at " +
            "      FROM tbl_exchange_status_history " +
            "      GROUP BY exchange_code) latest_status " +
            "ON B.exchange_code = latest_status.exchange_code " +
            "AND B.created_at = latest_status.max_created_at " +
            "WHERE B.exchange_code = :exchangeCode", nativeQuery = true)
    Optional<ExchangeStatus> findStatusBy(@Param("exchangeCode") int exchangeCode);

}
