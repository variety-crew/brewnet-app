package com.varc.brewnetapp.domain.correspondent.query.mapper;

import com.varc.brewnetapp.domain.correspondent.common.SearchCorrespondentCriteria;
import com.varc.brewnetapp.domain.correspondent.query.dto.CorrespondentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CorrespondentMapper {

    List<CorrespondentDTO> searchCorrespondents(SearchCorrespondentCriteria criteria);

    int getTotalCorrespondentCount(SearchCorrespondentCriteria criteria);
}
