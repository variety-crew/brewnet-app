package com.varc.brewnetapp.domain.auth.query.mapper;

import com.varc.brewnetapp.domain.auth.query.vo.MemberVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthenticationMapper {
    MemberVO selectMemberByIdWithAuthorities(String id);

    List<String> selectAuths();
}
