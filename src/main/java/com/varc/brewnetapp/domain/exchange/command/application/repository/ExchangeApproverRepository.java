package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeApprover;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeApproverCode;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItem;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningApprover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ExchangeApproverRepositoryCommand")
public interface ExchangeApproverRepository extends JpaRepository<ExchangeApprover, ExchangeApproverCode> {
    @Query("SELECT e FROM ExchangeApprover e WHERE e.exchangeApproverCode.exchangeCode = :exchangeCode")
    Optional<List<ExchangeApprover>> findByExchangeCode(@Param("exchangeCode") int exchangeCode);
}
