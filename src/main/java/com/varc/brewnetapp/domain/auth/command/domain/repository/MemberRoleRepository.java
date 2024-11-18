package com.varc.brewnetapp.domain.auth.command.domain.repository;


import com.varc.brewnetapp.domain.auth.command.domain.aggregate.entity.MemberRole;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.MemberRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, MemberRolePK> {
}
