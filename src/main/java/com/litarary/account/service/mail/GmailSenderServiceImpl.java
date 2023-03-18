package com.litarary.account.service.mail;

import com.litarary.account.domain.AuthCodeGenerator;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class GmailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender gmailSender;

    @Override
    public void sendAuthCode(String email) {

        MimeMessage mimeMessage = gmailSender.createMimeMessage();
        sendMessage(email, mimeMessage);
    }

    private void sendMessage(String email, MimeMessage mimeMessage) {
        try {
            String authCode = AuthCodeGenerator.generateCode();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Litarary 이메일 인증문자");
            String message = String.format("안녕하세요! Litarary에 방문해주셔서 감사합니다. \n 이메일 인증문자 입니다. 확인 후 가입 부탁드립니다. \n\n 인증문자 : %s", authCode);
            mimeMessageHelper.setText(message);
            gmailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            log.warn("Gmail message error : {}", ex.getMessage());
            throw new LitararyErrorException(ErrorCode.GMAIL_SENDER_ERROR, ex.getMessage());
        }
    }
}
