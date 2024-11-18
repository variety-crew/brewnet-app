package com.varc.brewnetapp.domain.auth.command.application.service;

public interface RedisService {

    void saveEmailCode(String sendEmail, String number);

    String getEmailCode(String email);
}
