package com.varc.brewnetapp.domain.auth.command.domain.repository;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAuthRepository extends JpaRepository<Member, Integer> {
}
