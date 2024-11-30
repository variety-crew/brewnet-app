package com.varc.brewnetapp.domain.returning.command.domain.repository;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.Returning;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningRefundHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("ReturningRefundHistoryRepositoryCommand")
public interface ReturningRefundHistoryRepository extends JpaRepository<ReturningRefundHistory, Integer> {
    Optional<ReturningRefundHistory> findByReturning(Returning returning);
}
