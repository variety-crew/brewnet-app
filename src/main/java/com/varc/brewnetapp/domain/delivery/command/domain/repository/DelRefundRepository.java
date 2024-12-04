package com.varc.brewnetapp.domain.delivery.command.domain.repository;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DelRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelRefundRepository extends JpaRepository<DelRefund, Integer> {

}