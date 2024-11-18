package com.varc.brewnetapp.domain.document.command.application.service;

import com.varc.brewnetapp.domain.document.command.application.dto.ApproverRequestDTO;
import com.varc.brewnetapp.domain.document.command.domain.aggregate.ApprovalKind;
import com.varc.brewnetapp.domain.document.command.domain.aggregate.entity.Approval;
import com.varc.brewnetapp.domain.document.command.domain.repository.ApprovalRepository;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Position;
import com.varc.brewnetapp.domain.member.command.domain.repository.PositionRepository;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service(value = "commandApprovalService")
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final PositionRepository positionRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public ApprovalService(
        ApprovalRepository approvalRepository,
        PositionRepository positionRepository,
        JwtUtil jwtUtil)
    {
        this.approvalRepository = approvalRepository;
        this.positionRepository = positionRepository;
        this.jwtUtil = jwtUtil;
    }

    public void createApprover(String accessToken, ApproverRequestDTO approverRequestDTO) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            Integer positionCode = null;
            ApprovalKind approvalKind = null;

            if(approverRequestDTO.getPositionName().equals("대리")) {
                positionCode = positionRepository.findByName(PositionName.ASSISTANT_MANAGER)
                    .orElse(null).getPositionCode();
            }
            else if (approverRequestDTO.getPositionName().equals("과장")) {
                positionCode = positionRepository.findByName(PositionName.MANAGER)
                    .orElse(null).getPositionCode();
            }
            else if (approverRequestDTO.getPositionName().equals("대표이사")) {
                positionCode = positionRepository.findByName(PositionName.CEO)
                    .orElse(null).getPositionCode();
            }
            else
                throw new InvalidDataException("잘못된 직급입니다");

            if(approverRequestDTO.getKind().equals("주문"))
                approvalKind = ApprovalKind.ORDER;
            else if (approverRequestDTO.getKind().equals("반품"))
                approvalKind = ApprovalKind.RETURN;
            else if (approverRequestDTO.getKind().equals("교환"))
                approvalKind = ApprovalKind.EXCHANGE;
            else if (approverRequestDTO.getKind().equals("발주"))
                approvalKind = ApprovalKind.PURCHASE;
            else
                throw new InvalidDataException("잘못된 결재 구분값입니다");

            Approval approval = approvalRepository.findByKindAndSequence(approvalKind, approverRequestDTO.getSeq()).orElse(null);

            if(approval == null){
                Approval newApproval = Approval.builder()
                    .kind(approvalKind)
                    .sequence(approverRequestDTO.getSeq())
                    .positionCode(positionCode)
                    .createdAt(LocalDateTime.now())
                    .active(true).build();

                approvalRepository.save(newApproval);
            }
            else {
                approval.setPositionCode(positionCode);
                approvalRepository.save(approval);
            }

        } else
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");

    }

    public void deleteApprover(String accessToken, ApproverRequestDTO approverRequestDTO) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            ApprovalKind approvalKind = null;
            
            if(approverRequestDTO.getKind().equals("주문"))
                approvalKind = ApprovalKind.ORDER;
            else if (approverRequestDTO.getKind().equals("반품"))
                approvalKind = ApprovalKind.RETURN;
            else if (approverRequestDTO.getKind().equals("교환"))
                approvalKind = ApprovalKind.EXCHANGE;
            else if (approverRequestDTO.getKind().equals("발주"))
                approvalKind = ApprovalKind.PURCHASE;
            else
                throw new InvalidDataException("잘못된 결재 구분값입니다");

            Approval approval = approvalRepository.findByKindAndSequence(approvalKind, approverRequestDTO.getSeq()).orElse(null);

            if(approval == null)
                throw new InvalidDataException("잘못된 결재 구분값과 순번 값을 입력하셨습니다");
            else
                approvalRepository.delete(approval);

        } else
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");
    }
}
