package com.varc.brewnetapp.domain.member.command.domain.repository;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findById(String id);
}
