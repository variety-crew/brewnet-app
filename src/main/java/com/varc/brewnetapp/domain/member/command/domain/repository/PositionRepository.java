package com.varc.brewnetapp.domain.member.command.domain.repository;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.Position;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {

    Optional <Position> findByName(PositionName position);
}
