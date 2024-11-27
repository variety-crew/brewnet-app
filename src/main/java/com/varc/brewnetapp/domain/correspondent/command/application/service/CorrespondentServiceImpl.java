package com.varc.brewnetapp.domain.correspondent.command.application.service;

import com.varc.brewnetapp.domain.correspondent.command.application.dto.CorrespondentRequestDTO;
import com.varc.brewnetapp.domain.correspondent.command.domain.aggregate.Correspondent;
import com.varc.brewnetapp.domain.correspondent.command.domain.repository.CorrespondentItemRepository;
import com.varc.brewnetapp.domain.correspondent.command.domain.repository.CorrespondentRepository;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void createCorrespondent(String loginId, CorrespondentRequestDTO newCorrespondent) {

        // 로그인한 사용자 체크
        memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        // 새로운 거래처 저장
        Correspondent correspondent = modelMapper.map(newCorrespondent, Correspondent.class);
        correspondent.setActive(true);
        correspondent.setCreatedAt(LocalDateTime.now());
        correspondentRepository.save(correspondent);
    }
}
