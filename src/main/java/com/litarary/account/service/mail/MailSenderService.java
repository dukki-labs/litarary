package com.litarary.account.service.mail;

public interface MailSenderService {

    String sendAuthCode(String email, String authCode);
}
