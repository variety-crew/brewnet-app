package com.varc.brewnetapp.domain.document.query.mapper;

import com.varc.brewnetapp.domain.document.query.dto.ApprovalDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DocumentMapper {
    List<ApprovalDTO> selectApprovalList();
}
