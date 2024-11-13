package com.varc.brewnetapp.domain.auth.command.domain.repository;


import com.varc.brewnetapp.domain.member.command.domain.aggregate.MemberRole;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.MemberRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAuthRepository extends JpaRepository<MemberRole, MemberRolePK> {
}
