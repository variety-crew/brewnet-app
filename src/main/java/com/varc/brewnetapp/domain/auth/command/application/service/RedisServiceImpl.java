package com.varc.brewnetapp.domain.auth.command.application.service;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "commandRedisService")
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate redisTemplate;

    @Value("${mail.expiration_time}")
    private Long emailCodeExpireTime;

    @Autowired
    public RedisServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 이메일 인증번호 저장
    @Override
    @Transactional
    public void saveEmailCode(String email, String number) {
        redisTemplate.opsForValue().set(email, number, emailCodeExpireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    @Transactional
    public String getEmailCode(String email) {
        return redisTemplate.opsForValue().get(email).toString();
    }
}
