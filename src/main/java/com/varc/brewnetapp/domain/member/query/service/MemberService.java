package com.varc.brewnetapp.domain.member.query.service;

import com.varc.brewnetapp.domain.member.query.dto.CompanyDTO;
import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import com.varc.brewnetapp.domain.member.query.dto.SealDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    Page<MemberDTO> findMemberList(Pageable page);

    MemberDTO findMember(String accessToken);
}
