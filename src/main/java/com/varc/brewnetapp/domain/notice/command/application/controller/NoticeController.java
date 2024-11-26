package com.varc.brewnetapp.domain.notice.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.member.command.application.dto.SendEmailRequestDTO;
import com.varc.brewnetapp.domain.notice.command.application.dto.CreateNoticeRequestDTO;
import com.varc.brewnetapp.domain.notice.command.application.dto.UpdateNoticeRequestDTO;
import com.varc.brewnetapp.domain.notice.command.application.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ResponseMessage<Object>> createNotice(@RequestPart CreateNoticeRequestDTO createNoticeRequestDTO,
        @RequestPart List<MultipartFile> image,
        @RequestHeader("Authorization") String accessToken) {

        noticeService.createNotice(createNoticeRequestDTO, image, accessToken);
        return ResponseEntity.ok(new ResponseMessage<>(200, "공지사항 작성에 성공했습니다", null));
    }

    // 마스터만
    @PutMapping("master/notice")
    @Operation(summary = "공지사항 수정 API ")
    public ResponseEntity<ResponseMessage<Object>> updateNotice(@RequestPart UpdateNoticeRequestDTO updateNoticeRequestDTO,
        @RequestPart List<MultipartFile> image,
        @RequestHeader("Authorization") String accessToken) {

        noticeService.updateNotice(updateNoticeRequestDTO, image, accessToken);
        return ResponseEntity.ok(new ResponseMessage<>(200, "공지사항 수정에 성공했습니다", null));
    }
}
