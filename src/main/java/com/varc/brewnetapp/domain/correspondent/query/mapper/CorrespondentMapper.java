package com.varc.brewnetapp.domain.correspondent.query.mapper;

import com.varc.brewnetapp.domain.correspondent.common.SearchCorrespondentCriteria;
import com.varc.brewnetapp.domain.correspondent.common.SearchCorrespondentItemCriteria;
import com.varc.brewnetapp.domain.correspondent.query.dto.CorrespondentDTO;
import com.varc.brewnetapp.domain.correspondent.query.dto.CorrespondentItemDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CorrespondentMapper {

    List<CorrespondentDTO> searchCorrespondents(SearchCorrespondentCriteria criteria);

    int getTotalCorrespondentCount(SearchCorrespondentCriteria criteria);

    List<CorrespondentItemDTO> searchCorrespondentItems(SearchCorrespondentItemCriteria criteria);

    int getTotalCorrespondentItemCount(SearchCorrespondentItemCriteria criteria);

    List<CorrespondentDTO> printCorrespondentList(SearchCorrespondentCriteria criteria);

    List<CorrespondentItemDTO> printCorrespondentItemList(SearchCorrespondentItemCriteria criteria);
}
