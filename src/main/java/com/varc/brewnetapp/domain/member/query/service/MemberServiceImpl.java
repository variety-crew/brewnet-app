package com.varc.brewnetapp.domain.member.query.service;

import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import com.varc.brewnetapp.domain.member.query.mapper.MemberMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = "queryMemberService")
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    @Transactional
    public Page<MemberDTO> findMemberList(Pageable page) {
        // 페이징 정보 추가
        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = (pageNumber - 1) * pageSize;

        // DB에서 교환 목록 조회
        List<MemberDTO> memberList = memberMapper.selectMemberList(offset, pageSize);
        memberList.stream().forEach(member -> {
            if(member.getPositionName().equals("STAFF"))
                member.setPositionName("사원");
            else if(member.getPositionName().equals("ASSISTANT_MANAGER"))
                member.setPositionName("대리");
            else if(member.getPositionName().equals("MANAGER"))
                member.setPositionName("과장");
            else if(member.getPositionName().equals("CEO"))
                member.setPositionName("대표이사");
        });

        // 전체 데이터 개수 조회
        int count = memberMapper.selectMemberListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(memberList, page, count);
    }


}
