package com.varc.brewnetapp.domain.notice.query.service;

import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import com.varc.brewnetapp.domain.notice.query.dto.NoticeDTO;
import com.varc.brewnetapp.domain.notice.query.mapper.NoticeMapper;
import com.varc.brewnetapp.exception.EmptyDataException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "queryNoticeService")
public class NoticeService{

    private final NoticeMapper noticeMapper;

    @Autowired
    public NoticeService(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Transactional
    public Page<NoticeDTO> getNoticeList(Pageable page, String title, String writerName, String sort) {
        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = pageNumber * pageSize;

        // DB에서 교환 목록 조회
        List<NoticeDTO> noticeList = noticeMapper.selectNoticeList(offset, pageSize, title, writerName, sort);

        if (noticeList == null || noticeList.isEmpty())
            throw new EmptyDataException("조회하려는 공지 목록이 없습니다");

        // 전체 데이터 개수 조회
        int count = noticeMapper.selectNoticeListCnt(title, writerName, sort);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(noticeList, page, count);

    }

    @Transactional
    public NoticeDTO getNotice(int noticeCode) {
        NoticeDTO notice = noticeMapper.selectNotice(noticeCode);

        if (notice == null)
            throw new EmptyDataException("조회하려는 공지가 없습니다");

        return notice;
    }
}
