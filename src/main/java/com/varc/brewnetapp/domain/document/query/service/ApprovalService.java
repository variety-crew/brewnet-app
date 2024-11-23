package com.varc.brewnetapp.domain.document.query.service;

import com.varc.brewnetapp.domain.document.query.dto.ApproverDTO;
import com.varc.brewnetapp.domain.document.query.mapper.DocumentMapper;
import com.varc.brewnetapp.domain.document.query.dto.ApprovalDTO;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "queryApprovalService")
public class ApprovalService {

    private final DocumentMapper documentMapper;

    @Autowired
    public ApprovalService(DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    @Transactional
    public List<ApprovalDTO> findApprovalList() {
        List<ApprovalDTO> approvalList = documentMapper.selectApprovalList();

        return approvalList;
    }

    public List<ApproverDTO> findApproverlList() {
        List<ApproverDTO> approverList = documentMapper.selectApproverList();

        return approverList;
    }
}
