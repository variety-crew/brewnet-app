package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeApprover;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeApproverCode;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExchangeApproverRepositoryCommand")
public interface ExchangeApproverRepository extends JpaRepository<ExchangeApprover, ExchangeApproverCode> {

}
