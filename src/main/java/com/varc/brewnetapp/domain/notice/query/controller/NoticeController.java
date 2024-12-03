package com.varc.brewnetapp.domain.notice.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.notice.query.dto.NoticeDTO;
import com.varc.brewnetapp.domain.notice.query.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "queryNoticeController")
@RequestMapping("api/v1/hq/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @GetMapping("")
    @Operation(summary = "공지사항 목록 조회 API. / sort 값은 dateASC or dateDESC 보내주시면 됩니다")
    public ResponseEntity<ResponseMessage<Page<NoticeDTO>>> getNoticeList(@PageableDefault(page = 0, size = 10) Pageable page,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String writerName,
        @RequestParam(required = false) String sort
    ) {

        Page<NoticeDTO> result = noticeService.getNoticeList(page, title, writerName, sort);
        return ResponseEntity.ok(new ResponseMessage<>(200, "공지사항 목록 조회에 성공했습니다", result));
    }

    @GetMapping("{noticeCode}")
    @Operation(summary = "공지사항 상세 조회 API")
    public ResponseEntity<ResponseMessage<NoticeDTO>> getNotice(@PathVariable(value = "noticeCode") int noticeCode
    ) {

        NoticeDTO result = noticeService.getNotice(noticeCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "공지사항 상세 조회에 성공했습니다", result));
    }
}
