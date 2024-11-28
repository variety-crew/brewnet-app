package com.varc.brewnetapp.domain.returning.command.domain.repository;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ReturningItemRepositoryCommand")
public interface ReturningItemRepository extends JpaRepository<ReturningItem, Integer> {
//    List<ReturningItem> findByReturningItemCode_ReturningCode(int returningCode);
}
