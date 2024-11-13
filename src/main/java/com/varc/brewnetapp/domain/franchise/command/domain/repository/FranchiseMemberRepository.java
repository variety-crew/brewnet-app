package com.varc.brewnetapp.domain.franchise.command.domain.repository;

import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.FranchiseMemberId;
import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity.FranchiseMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseMemberRepository extends JpaRepository<FranchiseMember, FranchiseMemberId> {

}
