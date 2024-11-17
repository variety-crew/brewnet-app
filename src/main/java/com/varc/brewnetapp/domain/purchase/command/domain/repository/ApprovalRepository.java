package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.Approval;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, Integer> {
}
