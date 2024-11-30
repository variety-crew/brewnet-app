package com.varc.brewnetapp.domain.returning.command.domain.repository;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningApprover;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey.ReturningApproverCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ReturningApproverRepositoryCommand")

public interface ReturningApproverRepository extends JpaRepository<ReturningApprover, ReturningApproverCode> {
    @Query("SELECT r FROM ReturningApprover r WHERE r.returningApproverCode.returningCode = :returningCode")
    Optional<List<ReturningApprover>> findByReturningCode(@Param("returningCode") int returningCode);
}