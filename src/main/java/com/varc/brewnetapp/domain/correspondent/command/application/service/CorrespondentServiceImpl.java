package com.varc.brewnetapp.domain.correspondent.command.application.service;

import com.varc.brewnetapp.domain.correspondent.command.application.dto.CorrespondentDeleteRequestDTO;
import com.varc.brewnetapp.domain.correspondent.command.application.dto.CorrespondentRequestDTO;
import com.varc.brewnetapp.domain.correspondent.command.domain.aggregate.Correspondent;
import com.varc.brewnetapp.domain.correspondent.command.domain.repository.CorrespondentItemRepository;
import com.varc.brewnetapp.domain.correspondent.command.domain.repository.CorrespondentRepository;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.exception.CorrespondentNotFoundException;
import com.varc.brewnetapp.exception.InvalidApiRequestException;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("CorrespondentServiceCommand")
public class CorrespondentServiceImpl implements CorrespondentService{

    private final ModelMapper modelMapper;
    private final CorrespondentRepository correspondentRepository;
    private final CorrespondentItemRepository correspondentItemRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public CorrespondentServiceImpl(CorrespondentRepository correspondentRepository,
                                    CorrespondentItemRepository correspondentItemRepository,
                                    MemberRepository memberRepository,
                                    ModelMapper modelMapper) {
        this.correspondentRepository = correspondentRepository;
        this.correspondentItemRepository = correspondentItemRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void createCorrespondent(String loginId, CorrespondentRequestDTO newCorrespondent) {

        // 로그인한 사용자 체크
        memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        Correspondent existCorrespondent = correspondentRepository
                                            .findByNameAndActiveTrue(newCorrespondent.getCorrespondentName());
        if (existCorrespondent != null) {
            throw new InvalidDataException("해당 이름의 거래처가 이미 존재합니다.");
        }

        // 새로운 거래처 저장
        Correspondent correspondent = modelMapper.map(newCorrespondent, Correspondent.class);
        correspondent.setActive(true);
        correspondent.setCreatedAt(LocalDateTime.now());
        correspondentRepository.save(correspondent);
    }

    @Transactional
    @Override
    public void updateCorrespondent(String loginId, int correspondentCode, CorrespondentRequestDTO editCorrespondent) {

        // 로그인한 사용자 체크
        memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        // 존재하는 거래처인지, 삭제된 거래처가 아닌지 체크
        Correspondent correspondent = correspondentRepository.findById(correspondentCode)
                .orElseThrow(() -> new CorrespondentNotFoundException("존재하지 않는 거래처입니다."));

        if (!correspondent.getActive()) throw new CorrespondentNotFoundException("삭제된 거래처입니다.");

        // 수정된 정보 저장
        if (editCorrespondent.getCorrespondentName() != null)
            correspondent.setName(editCorrespondent.getCorrespondentName());
        if (editCorrespondent.getAddress() != null) correspondent.setAddress(editCorrespondent.getAddress());
        if (editCorrespondent.getDetailAddress() != null)
            correspondent.setDetailAddress(editCorrespondent.getDetailAddress());
        if (editCorrespondent.getEmail() != null) correspondent.setEmail(editCorrespondent.getEmail());
        if (editCorrespondent.getContact() != null) correspondent.setContact(editCorrespondent.getContact());
        if (editCorrespondent.getManagerName() != null)
            correspondent.setManagerName(editCorrespondent.getManagerName());
    }

    @Transactional
    @Override
    public void deleteCorrespondent(String loginId, CorrespondentDeleteRequestDTO deleteRequest) {

        // 로그인한 사용자 체크
        memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        // 존재하는 거래처인지, 삭제된 거래처가 아닌지 체크
        Correspondent correspondent = correspondentRepository.findById(deleteRequest.getCorrespondentCode())
                .orElseThrow(() -> new CorrespondentNotFoundException("존재하지 않는 거래처입니다."));

        if (!correspondent.getActive()) throw new CorrespondentNotFoundException("이미 삭제된 거래처입니다.");

        // 거래처와 연결된 상품이 없는지 체크
        if (correspondentItemRepository.existsByCorrespondentCodeAndActiveTrue(deleteRequest.getCorrespondentCode())) {
            throw new InvalidApiRequestException("해당 거래처에 등록된 상품이 존재합니다. 등록된 상품을 먼저 제거해 주세요.");
        }

        correspondent.setActive(false);
    }
}
