package com.litarary.account.controller;

import com.litarary.account.controller.dto.MemberDto;
import com.litarary.account.controller.dto.MemberLoginDto;
import com.litarary.account.controller.mapper.MemberMapper;
import com.litarary.account.service.AccountService;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.utils.TokenInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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
        TokenInfo login = accountService.login(request.getEmail(), request.getPassword());
        return  memberMapper.loginResponse(login);
    }
}
