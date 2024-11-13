package com.varc.brewnetapp.domain.auth.command.application.service;

import com.varc.brewnetapp.domain.auth.command.application.dto.ConfirmEmailRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.SendEmailRequestDTO;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.exception.InvalidEmailCodeException;
import com.varc.brewnetapp.exception.InvalidEmailException;
import io.lettuce.core.RedisException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(value = "commandEmailService")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    @Value("${mail.username}")
    private static String senderEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, RedisService redisService, MemberRepository memberRepository) {
        this.javaMailSender = javaMailSender;
        this.redisService = redisService;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void sendSimpleMessage(SendEmailRequestDTO sendEmailRequestDTO) throws MessagingException {
        String sendEmail = memberRepository.findById(sendEmailRequestDTO.getLoginId()).orElse(null).getEmail();

        if (!sendEmailRequestDTO.getEmail().equals(sendEmail))
            throw new InvalidEmailException("입력하신 로그인 아이디와 이메일이 일치하지 않습니다");

        String number = createNumber(); // 랜덤 인증번호 생성

        MimeMessage message = createMail(sendEmail, number); // 메일 생성
        try {
            javaMailSender.send(message); // 메일 발송
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("메일 발송 중 오류가 발생했습니다.");
        }

        // 레디스에 저장
        redisService.saveEmailCode(sendEmail, number);

    }

    @Override
    @Transactional
    public boolean findEmailCode(ConfirmEmailRequestDTO confirmEmailRequestDTO) {
        String redisEmailCode;

        try {
            // Redis에서 이메일 코드 조회
            redisEmailCode = redisService.getEmailCode(confirmEmailRequestDTO.getEmail());

            if (redisEmailCode == null)
                throw new InvalidEmailCodeException("이메일 인증 코드가 존재하지 않거나 만료되었습니다");
        } catch (RedisException e) {
            throw new InvalidEmailCodeException("Redis에서 이메일 코드가 정상적으로 삭제되지 않았습니다");
        } catch (Exception e) {
            // 일반적인 예외 처리
            throw new InvalidEmailCodeException("이메일 인증 과정에서 문제가 발생했습니다");
        }

        // 입력받은 코드와 Redis에서 조회된 코드를 비교
        return redisEmailCode.equals(confirmEmailRequestDTO.getCode());
    }


    @Transactional
    public String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) { // 인증 코드 8자리
            int index = random.nextInt(3); // 0~2까지 랜덤, 랜덤값으로 switch문 실행

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97)); // 소문자
                case 1 -> key.append((char) (random.nextInt(26) + 65)); // 대문자
                case 2 -> key.append(random.nextInt(10)); // 숫자
            }
        }
        return key.toString();
    }

    @Transactional
    public MimeMessage createMail(String mail, String number) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setSubject("이메일 인증");
        String body = "";
        body += "<h3>요청하신 인증 번호입니다.</h3>";
        body += "<h1>" + number + "</h1>";
        body += "<h3>감사합니다.</h3>";
        message.setText(body, "UTF-8", "html");

        return message;
    }




}
