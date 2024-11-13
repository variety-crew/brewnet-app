package com.varc.brewnetapp.domain.auth.command.domain.repository;

import com.varc.brewnetapp.domain.auth.command.domain.aggregate.RoleType;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(RoleType roleType);
}
