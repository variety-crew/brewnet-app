package com.varc.brewnetapp.domain.correspondent.command.application.service;

import com.varc.brewnetapp.domain.correspondent.command.domain.repository.CorrespondentItemRepository;
import com.varc.brewnetapp.domain.correspondent.command.domain.repository.CorrespondentRepository;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CorrespondentServiceCommand")
public class CorrespondentServiceImpl implements CorrespondentService{

    private final CorrespondentRepository correspondentRepository;
    private final CorrespondentItemRepository correspondentItemRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public CorrespondentServiceImpl(CorrespondentRepository correspondentRepository,
                                    CorrespondentItemRepository correspondentItemRepository,
                                    MemberRepository memberRepository) {
        this.correspondentRepository = correspondentRepository;
        this.correspondentItemRepository = correspondentItemRepository;
        this.memberRepository = memberRepository;
    }
}
