package com.varc.brewnetapp.domain.delivery.command.domain.repository;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryOrderStatusHistory.OrderStatus;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryReturnStatusHistory;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryReturnStatusHistory.ReturnStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryReturnStatusHistoryRepository extends
    JpaRepository<DeliveryReturnStatusHistory, Integer> {

    Optional<DeliveryReturnStatusHistory> findByReturnCodeAndStatus(int code, ReturnStatus status);
}
