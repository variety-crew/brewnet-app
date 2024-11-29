package com.varc.brewnetapp.domain.returning.command.domain.repository;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningItemStatus;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey.ReturningItemStatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ReturningItemStatusRepositoryCommand")
public interface ReturningItemStatusRepository extends JpaRepository<ReturningItemStatus, ReturningItemStatusCode> {
    List<ReturningItemStatus> findByReturningItemStatusCode_ReturningStockHistoryCode(int returningStockHistoryCode);

}
