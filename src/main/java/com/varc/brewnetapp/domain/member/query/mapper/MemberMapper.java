package com.varc.brewnetapp.domain.member.query.mapper;

import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    List<MemberDTO> selectMemberList(long offset, long pageSize);

    int selectMemberListCnt();


}
