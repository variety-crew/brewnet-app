package com.varc.brewnetapp.domain.sse.controller;

import com.varc.brewnetapp.domain.sse.dto.AlarmDTO;
import com.varc.brewnetapp.domain.sse.service.SSEService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("api/v1/sse")
public class SSEController {

    private final SSEService sseService;

    @Autowired
    public SSEController(SSEService sseService) {
        this.sseService = sseService;
    }

    /**
     * 클라이언트의 이벤트 구독을 수락한다. text/event-stream은 SSE를 위한 Mime Type이다. 서버 -> 클라이언트로 이벤트를 보낼 수 있게된다.
     */
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @Operation(summary = "SSE 구독 API. 처음 구독을 진행하면 구독완료 되었다는 알림 발송(시스템에서 보낸거라 안보여주셔야 합니다) "
        + " / 구독완료 시, 알림을 발송하지 않으면 해당 API 호출 시 무한 로딩됨.")
    public SseEmitter subscribe( @RequestHeader("Authorization") String accessToken) {
        return sseService.subscribe(accessToken);
    }

    /**
     * 이벤트를 구독 중인 클라이언트에게 데이터를 전송한다.
     */
    @PostMapping("/send-test/{memberCode}")
    @Operation(summary = "SSE 알림 전송 테스트 API. 서버에서 테스트를 위해 사용. 프론트에서 사용 X")
    public void broadcast(@PathVariable Integer memberCode, @RequestBody AlarmDTO alarmDTO) {
        sseService.sendToMember(memberCode, alarmDTO);
    }
}
