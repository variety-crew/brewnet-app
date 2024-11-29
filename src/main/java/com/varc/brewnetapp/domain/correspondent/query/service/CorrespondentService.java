package com.varc.brewnetapp.domain.correspondent.query.service;

import com.varc.brewnetapp.domain.correspondent.common.PageResponse;
import com.varc.brewnetapp.domain.correspondent.query.dto.CorrespondentDTO;
import com.varc.brewnetapp.domain.correspondent.query.dto.CorrespondentItemDTO;

import java.util.List;

public interface CorrespondentService {

    PageResponse<List<CorrespondentDTO>> selectAllCorrespondents(String loginId,
                                                                 Integer correspondentCode,
                                                                 String correspondentName,
                                                                 int pageNumber,
                                                                 int pageSize);

    PageResponse<List<CorrespondentItemDTO>> selectItemsOfCorrespondent(String loginId,
                                                                        Integer correspondentCode,
                                                                        String itemUniqueCode,
                                                                        String itemName,
                                                                        int pageNumber,
                                                                        int pageSize);

    List<CorrespondentDTO> printAllCorrespondents(Integer correspondentCode, String correspondentName);

    List<CorrespondentItemDTO> printCorrespondentActiveItems(Integer correspondentCode,
                                                             String itemUniqueCode,
                                                             String itemName);

    CorrespondentDTO getCorrespondentDetail(String correspondentCode);
}
