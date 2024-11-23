package com.varc.brewnetapp.domain.delivery.command.domain.repository;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryExchangeStatusHistory;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryExchangeStatusHistory.ExchangeStatus;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryReturnStatusHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryExchangeStatusHistoryRepository extends
    JpaRepository<DeliveryExchangeStatusHistory, Integer> {

    Optional<DeliveryExchangeStatusHistory> findByExchangeCodeAndStatus(int code, ExchangeStatus status);
}
