package com.varc.brewnetapp.domain.notice.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.member.command.application.dto.SendEmailRequestDTO;
import com.varc.brewnetapp.domain.notice.command.application.dto.CreateNoticeRequestDTO;
import com.varc.brewnetapp.domain.notice.command.application.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commandNoticeController")
@RequestMapping("api/v1")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 마스터만
    @PostMapping("master/notice")
    @Operation(summary = "공지사항 등록 API ")
    public ResponseEntity<ResponseMessage<Object>> createNotice(@RequestBody CreateNoticeRequestDTO createNoticeRequestDTO,
        @RequestHeader("Authorization") String accessToken) {

//        noticeService.createNotice(createNoticeRequestDTO, accessToken);
        return ResponseEntity.ok(new ResponseMessage<>(200, "인증 이메일 발송에 성공했습니다", null));
    }
}
