package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeApprover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ExchangeApproverRepositoryCommand")
public interface ExchangeApproverRepository extends JpaRepository<ExchangeApprover, Integer> {

}
