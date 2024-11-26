package com.varc.brewnetapp.domain.notice.command.domain.repository;

import com.varc.brewnetapp.domain.notice.command.domain.aggregate.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeImageRepository extends JpaRepository<NoticeImage, Integer> {

}
