package com.varc.brewnetapp.domain.correspondent.query.service;

import com.varc.brewnetapp.domain.correspondent.common.PageResponse;
import com.varc.brewnetapp.domain.correspondent.query.dto.CorrespondentDTO;

import java.util.List;

public interface CorrespondentService {

    PageResponse<List<CorrespondentDTO>> selectAllCorrespondents(String loginId,
                                                                 Integer correspondentCode,
                                                                 String correspondentName,
                                                                 int pageNumber,
                                                                 int pageSize);
}
