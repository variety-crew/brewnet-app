package com.varc.brewnetapp.domain.sse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FailedAlarmRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public FailedAlarmRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String getKey(Integer memberCode) {
        return "failed_alarms:" + memberCode;
    }

    // 알람 저장
    public void saveFailedAlarm(Integer memberCode, Object alarmData) {
        redisTemplate.opsForList().rightPush(getKey(memberCode), alarmData);
    }

    // 실패한 알람 조회
    public List<Object> getFailedAlarms(Integer memberCode) {
        return redisTemplate.opsForList().range(getKey(memberCode), 0, -1);
    }

    // 특정 회원의 실패한 알람 삭제
    public void clearFailedAlarms(Integer memberCode) {
        redisTemplate.delete(getKey(memberCode));
    }
}


