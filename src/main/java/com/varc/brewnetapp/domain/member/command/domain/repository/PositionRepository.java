package com.varc.brewnetapp.domain.member.command.domain.repository;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Position;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    Optional<Position> findByName(PositionName position);
}
