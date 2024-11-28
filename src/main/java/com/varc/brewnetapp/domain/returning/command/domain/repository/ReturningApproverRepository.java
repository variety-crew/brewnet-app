package com.varc.brewnetapp.domain.returning.command.domain.repository;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningApprover;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ReturningApproverRepositoryCommand")

public interface ReturningApproverRepository extends JpaRepository<ReturningApprover, Integer> {
}