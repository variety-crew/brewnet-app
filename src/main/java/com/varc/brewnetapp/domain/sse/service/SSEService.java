package com.varc.brewnetapp.domain.sse.service;

import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity.FranchiseMember;
import com.varc.brewnetapp.domain.franchise.command.domain.repository.FranchiseMemberRepository;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.sse.dto.RedisAlarmDTO;
import com.varc.brewnetapp.domain.sse.repository.FailedAlarmRepository;
import com.varc.brewnetapp.domain.sse.repository.SSERepository;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
public class SSEService {

    private final SSERepository sseRepository;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final FailedAlarmRepository failedAlarmRepository;
    private final FranchiseMemberRepository franchiseMemberRepository;

    @Autowired
    public SSEService(SSERepository sseRepository, JwtUtil jwtUtil,
        MemberRepository memberRepository,
        FailedAlarmRepository failedAlarmRepository,
        FranchiseMemberRepository franchiseMemberRepository) {
        this.sseRepository = sseRepository;
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
        this.failedAlarmRepository = failedAlarmRepository;
        this.franchiseMemberRepository = franchiseMemberRepository;
    }

    /**
     * 클라이언트의 이벤트 구독을 허용하는 메서드
     */
    public SseEmitter subscribe(String accessToken) throws InterruptedException {
        String senderLoginId = jwtUtil.getLoginId(accessToken.replace("Bearer ", ""));

        Integer memberCode = memberRepository.findById(senderLoginId)
            .orElseThrow(() -> new MemberNotFoundException("해당 토큰으로 회원 정보를 조회할 수 없습니다"))
            .getMemberCode();

        log.info("SSE 연결 시작입니다아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ");
        SseEmitter sseEmitter = sseRepository.findById(memberCode);

        if(sseEmitter == null)
            sseEmitter = sseRepository.save(memberCode, new SseEmitter(60L * 1000 * 60));

        /**
         // sse의 유효 시간이 만료되면, 클라이언트에서 다시 서버로 이벤트 구독을 시도한다.
         SseEmitter sseEmitter = sseRepository.save(memberCode, new SseEmitter(60L * 1000 * 60));
         */

        // 사용자에게 모든 데이터가 전송되었다면 emitter 삭제
        sseEmitter.onCompletion(() -> sseRepository.deleteById(memberCode));

        // Emitter의 유효 시간이 만료되면 emitter 삭제
        // 유효 시간이 만료되었다는 것은 클라이언트와 서버가 연결된 시간동안 아무런 이벤트가 발생하지 않은 것을 의미한다.
        sseEmitter.onTimeout(() -> sseRepository.deleteById(memberCode));

        // 첫 구독시에 이벤트를 발생시킨다.
        // sse 연결이 이루어진 후, 하나의 데이터로 전송되지 않는다면 sse의 유효 시간이 만료되고 503 에러가 발생한다.
        sendToMember(null, "SSE 구독 시작", memberCode, "Start Subscribe event, memberCode : " + memberCode);

        log.info("sse emitter입니다 " + sseEmitter.toString());

        // 저장된 알람 전송
        List<RedisAlarmDTO> failedAlarms = failedAlarmRepository.getFailedAlarms(memberCode);

        for (RedisAlarmDTO alarm : failedAlarms){
            log.info("for문 내부 sse emitter입니다 " + sseEmitter.toString());
            sendToMember(alarm.getSenderMemberCode(), "Past", memberCode, alarm.getMessage());
        }


        // 전송 후 삭제
        failedAlarmRepository.clearFailedAlarms(memberCode);

        log.info("모든 알림 전송완료입니다아아아아앙");
        return sseEmitter;
    }

    /** 특정 회원 1명에게 알림 발송하는 method */
    public void sendToMember(Integer senderMemberCode, String eventName, Integer recipientMemberCode, String data) {

        log.info("send to member 실행됨");
        if(senderMemberCode == recipientMemberCode)
            return;

        SseEmitter sseEmitter = sseRepository.findById(recipientMemberCode);

        if(data == null)
            data = senderMemberCode + "번 회원이" + recipientMemberCode + "번 회원에게 " + eventName + "알람을 발송하였습니다";

        RedisAlarmDTO redisAlarmDTO = new RedisAlarmDTO(data, eventName, senderMemberCode);

        if (sseEmitter == null) {
            // SSE 연결이 없으므로 알람을 Redis에 저장
            log.info("알림입니다 " + data);
            failedAlarmRepository.saveFailedAlarm(recipientMemberCode, redisAlarmDTO);
            return;
        }
        
        try {
            sseEmitter.send(
                SseEmitter.event()
                    .id(recipientMemberCode.toString())
                    .name(eventName)
                    .data(data)
            );
        } catch (IOException ex) {
            failedAlarmRepository.saveFailedAlarm(recipientMemberCode, redisAlarmDTO);
            sseRepository.deleteById(recipientMemberCode);
        }
    }

    /** 가맹점 유저들에게 알림 발송하는 method */
    public void sendToFranchise(Integer senderMemberCode, String eventName, String data) {

        List<FranchiseMember> franchiseMemberList = franchiseMemberRepository.findByActiveTrue();

        if(data == null)
            data = senderMemberCode + "번 회원이 가맹점 회원들에게 " + eventName + "알람을 발송하였습니다";

        RedisAlarmDTO redisAlarmDTO = new RedisAlarmDTO(data, eventName, senderMemberCode);

        for (FranchiseMember franchiseMember : franchiseMemberList) {
            if(senderMemberCode == franchiseMember.getMemberCode())
                continue;

            SseEmitter sseEmitter = sseRepository.findById(franchiseMember.getMemberCode());

            if (sseEmitter == null) {
                // SSE 연결이 없으므로 알람을 Redis에 저장
                failedAlarmRepository.saveFailedAlarm(franchiseMember.getMemberCode(), redisAlarmDTO);
                continue;
            }

            try {
                sseEmitter.send(
                    SseEmitter.event()
                        .id(franchiseMember.getMemberCode().toString())
                        .name(eventName)
                        .data(data)
                );
            } catch (IOException ex) {
                failedAlarmRepository.saveFailedAlarm(franchiseMember.getMemberCode(), redisAlarmDTO);
                sseRepository.deleteById(franchiseMember.getMemberCode());
            }
        }

    }

    /** 본사 유저들에게 알림 발송하는 method */
    public void sendToHq(Integer senderMemberCode, String eventName, String data) {

        List<Member> memberList = memberRepository.findByActiveTrueAndPositionCodeIsNotNull();

        if(data == null)
            data = senderMemberCode + "번 회원이 본사 회원들에게 " + eventName + "알람을 발송하였습니다";

        RedisAlarmDTO redisAlarmDTO = new RedisAlarmDTO(data, eventName, senderMemberCode);

        for (Member member : memberList) {
            if(senderMemberCode == member.getMemberCode())
                continue;

            SseEmitter sseEmitter = sseRepository.findById(member.getMemberCode());

            if (sseEmitter == null) {
                // SSE 연결이 없으므로 알람을 Redis에 저장
                failedAlarmRepository.saveFailedAlarm(member.getMemberCode(), redisAlarmDTO);
                continue;
            }

            try {
                sseEmitter.send(
                    SseEmitter.event()
                        .id(member.getMemberCode().toString())
                        .name(eventName)
                        .data(data)
                );
            } catch (IOException ex) {
                failedAlarmRepository.saveFailedAlarm(member.getMemberCode(), redisAlarmDTO);
                sseRepository.deleteById(member.getMemberCode());
            }
        }
    }

    public SseEmitter subscribeTest(int memberCode) {

        log.info("SSE 연결 시작입니다아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ");
        SseEmitter sseEmitter = sseRepository.findById(memberCode);

        if(sseEmitter == null)
            sseEmitter = sseRepository.save(memberCode, new SseEmitter(60L * 1000 * 60));

        /**
         // sse의 유효 시간이 만료되면, 클라이언트에서 다시 서버로 이벤트 구독을 시도한다.
         SseEmitter sseEmitter = sseRepository.save(memberCode, new SseEmitter(60L * 1000 * 60));
         */

        // 사용자에게 모든 데이터가 전송되었다면 emitter 삭제
        sseEmitter.onCompletion(() -> sseRepository.deleteById(memberCode));

        // Emitter의 유효 시간이 만료되면 emitter 삭제
        // 유효 시간이 만료되었다는 것은 클라이언트와 서버가 연결된 시간동안 아무런 이벤트가 발생하지 않은 것을 의미한다.
        sseEmitter.onTimeout(() -> sseRepository.deleteById(memberCode));

        // 첫 구독시에 이벤트를 발생시킨다.
        // sse 연결이 이루어진 후, 하나의 데이터로 전송되지 않는다면 sse의 유효 시간이 만료되고 503 에러가 발생한다.
        sendToMember(null, "SSE 구독 시작", memberCode, "Start Subscribe event, memberCode : " + memberCode);

        log.info("sse emitter입니다 " + sseEmitter.toString());

        // 저장된 알람 전송
        List<RedisAlarmDTO> failedAlarms = failedAlarmRepository.getFailedAlarms(memberCode);

        for (RedisAlarmDTO alarm : failedAlarms){
            log.info("for문 내부 sse emitter입니다 " + sseEmitter.toString());
            sendToMember(alarm.getSenderMemberCode(), "Past", memberCode, alarm.getMessage());
        }


        // 전송 후 삭제
        failedAlarmRepository.clearFailedAlarms(memberCode);

        log.info("모든 알림 전송완료입니다아아아아앙");
        return sseEmitter;
    }

    public String hash() {
        return sseRepository.emitters();
    }
}
