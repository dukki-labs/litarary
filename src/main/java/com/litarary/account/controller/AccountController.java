package com.litarary.account.controller;

import com.litarary.account.controller.dto.MemberDto;
import com.litarary.account.controller.dto.MemberLoginDto;
import com.litarary.account.controller.dto.MemberTokenDto;
import com.litarary.account.controller.mapper.MemberMapper;
import com.litarary.account.service.AccountService;
import com.litarary.account.service.dto.LoginInfo;
import com.litarary.account.service.dto.RefreshTokenInfo;
import com.litarary.account.service.dto.SignUpMemberInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/account")
public class AccountController {

    private final AccountService accountService;
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
        return  memberMapper.loginResponse(loginInfo);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/token-refresh")
    public MemberTokenDto.Response refreshToken(@Valid @RequestBody MemberTokenDto.Request request) {
        RefreshTokenInfo refreshTokenInfo = accountService.refreshToken(request.getEmail(), request.getRefreshToken());
        return memberMapper.refreshTokenResponse(refreshTokenInfo);
    }

}
