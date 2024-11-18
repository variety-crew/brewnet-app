package com.varc.brewnetapp.domain.auth.command.application.service;

import com.varc.brewnetapp.domain.auth.command.application.dto.ConfirmEmailRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.SendEmailRequestDTO;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendSimpleMessage(SendEmailRequestDTO sendEmailRequestDTO) throws MessagingException;

    boolean findEmailCode(ConfirmEmailRequestDTO confirmEmailRequestDTO);
}
