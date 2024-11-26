package com.varc.brewnetapp.domain.notice.query.mapper;

import com.varc.brewnetapp.domain.notice.query.dto.NoticeDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NoticeMapper {

    List<NoticeDTO> selectNoticeList(@Param(value = "offset") long offset, @Param(value = "pageSize")long pageSize,
        @Param(value = "title") String title, @Param(value = "writerName") String writerName,
        @Param(value = "sort") String sort);

    int selectNoticeListCnt(@Param(value = "title") String title,
        @Param(value = "writerName") String writerName, @Param(value = "sort") String sort);

    NoticeDTO selectNotice(@Param(value = "noticeCode") int noticeCode);
}
