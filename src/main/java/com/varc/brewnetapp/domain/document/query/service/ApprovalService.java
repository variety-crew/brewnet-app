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

        for (ApprovalDTO approval : approvalList) {
            if(approval.getPositionName().equals("STAFF"))
                approval.setPositionName("사원");
            else if(approval.getPositionName().equals("ASSISTANT_MANAGER"))
                approval.setPositionName("대리");
            else if(approval.getPositionName().equals("MANAGER"))
                approval.setPositionName("과장");
            else if(approval.getPositionName().equals("CEO"))
                approval.setPositionName("대표이사");

        }
        return approvalList;
    }

    public List<ApproverDTO> findApproverlList() {
        List<ApproverDTO> approverList = documentMapper.selectApproverList();

        for (ApproverDTO approver : approverList) {
            if(approver.getPositionName().equals("STAFF"))
                approver.setPositionName("사원");
            else if(approver.getPositionName().equals("ASSISTANT_MANAGER"))
                approver.setPositionName("대리");
            else if(approver.getPositionName().equals("MANAGER"))
                approver.setPositionName("과장");
            else if(approver.getPositionName().equals("CEO"))
                approver.setPositionName("대표이사");
        }
        return approverList;
    }
}
