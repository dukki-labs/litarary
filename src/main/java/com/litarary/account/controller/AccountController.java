package com.litarary.account.controller;

import com.litarary.account.controller.dto.*;
import com.litarary.account.controller.mapper.MemberMapper;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.service.AccountService;
import com.litarary.account.service.dto.LoginInfo;
import com.litarary.account.service.dto.RefreshTokenInfo;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.account.service.mail.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final MailSenderService mailSenderService;
    private final MemberMapper memberMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody MemberDto.Request memberRequest) {
        SignUpMemberInfo memberInfo = memberMapper.mapToSignUpMember(memberRequest);
        accountService.signUpMember(memberInfo);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public MemberLoginDto.Response login(@Valid @RequestBody MemberLoginDto.Request request) {
        LoginInfo loginInfo = accountService.login(request.getEmail(), request.getPassword());
        return memberMapper.loginResponse(loginInfo);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/password/send-code")
    public MemberEmailDto.Response emailCertification(@Valid @RequestBody MemberEmailDto.Request request) {
        Member member = accountService.findMember(request.getEmail());
        String authCode = mailSenderService.sendAuthCode(request.getEmail());
        accountService.updateAuthCode(member.getId(), authCode);
        return memberMapper.memberResponse(member);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("access-code")
    public void updateAccessCode(@Valid @RequestBody MemberAccessCode.Request request) {
        accountService.updateAuthCode(request.getMemberId(), request.getAccessCode());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/password")
    public void updatePassword(@Valid @RequestBody MemberPasswordDto.Request request) {
        accountService.updatePassword(request.getMemberId(), request.getAccessCode(), request.getPassword());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/send-code")
    public void sendAuthCode(@Valid @RequestBody MemberEmailDto.Request request) {
        accountService.validDuplicatedEmail(request.getEmail());
        mailSenderService.sendAuthCode(request.getEmail());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/token-refresh")
    public MemberTokenDto.Response refreshToken(@Valid @RequestBody MemberTokenDto.Request request) {
        RefreshTokenInfo refreshTokenInfo = accountService.refreshToken(request.getEmail(), request.getRefreshToken());
        return memberMapper.refreshTokenResponse(refreshTokenInfo);
    }
}
