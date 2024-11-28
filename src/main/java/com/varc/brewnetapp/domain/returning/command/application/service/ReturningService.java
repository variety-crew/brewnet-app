package com.varc.brewnetapp.domain.returning.command.application.service;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo.ReturningReqVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReturningService {
    Integer franCreateReturning(String loginId, ReturningReqVO returningReqVO, List<MultipartFile> returningImageList);

    void franCancelReturning(String loginId, Integer returningCode);
}
