package com.varc.brewnetapp.domain.returning.command.domain.repository;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturningStatusHistoryRepository extends JpaRepository<ReturningStatusHistory, Integer>{
}