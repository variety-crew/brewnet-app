package com.varc.brewnetapp.domain.correspondent.command.domain.repository;

import com.varc.brewnetapp.domain.correspondent.command.domain.aggregate.CorrespondentItem;
import com.varc.brewnetapp.domain.correspondent.command.domain.aggregate.CorrespondentItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrespondentItemRepository extends JpaRepository<CorrespondentItem, CorrespondentItemId> {

    boolean existsByCorrespondentCodeAndItemCodeAndActiveTrue(int correspondentCode, int itemCode);
}
