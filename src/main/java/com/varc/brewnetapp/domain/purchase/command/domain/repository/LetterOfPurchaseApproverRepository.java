package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.LetterOfPurchaseApprover;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.LetterOfPurchaseApproverId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterOfPurchaseApproverRepository extends JpaRepository<LetterOfPurchaseApprover, LetterOfPurchaseApproverId> {
}
