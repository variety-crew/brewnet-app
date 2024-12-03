package com.varc.brewnetapp.domain.returning.command.domain.repository;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.Returning;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("ReturningStockHistoryRepositoryCommand")
public interface ReturningStockHistoryRepository extends JpaRepository<ReturningStockHistory, Integer> {
    Optional<ReturningStockHistory> findByReturning(Returning returning);
}
