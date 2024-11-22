package com.varc.brewnetapp.domain.correspondent.query.service;

import com.varc.brewnetapp.domain.correspondent.common.PageResponse;
import com.varc.brewnetapp.domain.correspondent.common.SearchCorrespondentCriteria;
import com.varc.brewnetapp.domain.correspondent.query.dto.CorrespondentDTO;
import com.varc.brewnetapp.domain.correspondent.query.mapper.CorrespondentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CorrespondentServiceQuery")
public class CorrespondentServiceImpl implements CorrespondentService{

    private final CorrespondentMapper correspondentMapper;

    @Autowired
    public CorrespondentServiceImpl(CorrespondentMapper correspondentMapper) {
        this.correspondentMapper = correspondentMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<List<CorrespondentDTO>> selectAllCorrespondents(String loginId,
                                                                        Integer correspondentCode,
                                                                        String correspondentName,
                                                                        int pageNumber,
                                                                        int pageSize) {

        SearchCorrespondentCriteria criteria = new SearchCorrespondentCriteria();
        criteria.setCorrespondentCode(correspondentCode);
        criteria.setCorrespondentName(correspondentName);
        criteria.setPageNumber(pageNumber);
        criteria.setPageSize(pageSize);

        int offset = (pageNumber - 1) * pageSize;
        criteria.setOffset(offset);

        List<CorrespondentDTO> correspondents = correspondentMapper.searchCorrespondents(criteria);
        int totalCount = correspondentMapper.getTotalCorrespondentCount(criteria);
        PageResponse<List<CorrespondentDTO>> response = new PageResponse<>(
                                                                correspondents, pageNumber, pageSize, totalCount);

        return response;
    }
}
