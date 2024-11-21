package com.varc.brewnetapp.domain.member.query.service;

import com.varc.brewnetapp.domain.member.query.dto.CompanyDTO;
import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import com.varc.brewnetapp.domain.member.query.dto.OrderPrintDTO;
import com.varc.brewnetapp.domain.member.query.dto.SealDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    Page<MemberDTO> findMemberList(Pageable page, String search);

    MemberDTO findMember(String accessToken);

    Page<OrderPrintDTO> findSealHistory(Pageable page, String startDate, String endDate);
}
