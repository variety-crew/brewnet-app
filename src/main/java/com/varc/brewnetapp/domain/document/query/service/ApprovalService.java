package com.varc.brewnetapp.domain.document.query.service;

import com.varc.brewnetapp.domain.document.query.dto.ApproverDTO;
import com.varc.brewnetapp.domain.document.query.dto.ApproverMemberDTO;
import com.varc.brewnetapp.domain.document.query.mapper.DocumentMapper;
import com.varc.brewnetapp.domain.document.query.dto.ApprovalDTO;
import com.varc.brewnetapp.domain.purchase.common.KindOfApproval;
import com.varc.brewnetapp.exception.ApprovalNotFoundException;
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

    @Transactional
    public List<ApproverDTO> findApproverlList() {
        List<ApproverDTO> approverList = documentMapper.selectApproverList();

        return approverList;
    }

    @Transactional
    public List<ApproverMemberDTO> selectApproverList(KindOfApproval approvalLine) {
        List<ApproverMemberDTO> approvers = documentMapper.selectApproversByKind(approvalLine);
        if (approvers == null) throw new ApprovalNotFoundException("존재하지 않는 결재라인입니다.");

        return approvers;
    }
}
