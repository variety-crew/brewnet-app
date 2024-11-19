package com.varc.brewnetapp.domain.member.query.mapper;

import com.varc.brewnetapp.domain.member.query.dto.CompanyDTO;
import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import com.varc.brewnetapp.domain.member.query.dto.SealDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    List<MemberDTO> selectMemberList(long offset, long pageSize);

    int selectMemberListCnt();

    CompanyDTO selectCompany();

    SealDTO selectCompanySeal();

    MemberDTO selectMember(String loginId);
}
