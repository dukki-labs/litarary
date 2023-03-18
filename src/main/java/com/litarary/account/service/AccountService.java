package com.litarary.account.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.domain.entity.MemberRole;
import com.litarary.account.repository.AccountRepository;
import com.litarary.account.repository.MemberRoleRepository;
import com.litarary.account.service.dto.LoginInfo;
import com.litarary.account.service.dto.RefreshTokenInfo;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import com.litarary.utils.jwt.JwtTokenProvider;
import com.litarary.utils.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public void signUpMember(SignUpMemberInfo memberInfo) {
        validDuplicatedEmail(memberInfo.getMember().getEmail());

        Member member = memberInfo.getMember();
        member.updatePasswordEncode(passwordEncoder.encode(member.getPassword()));
        Member signUpMember = accountRepository.save(member);

        List<MemberRole> memberRoles = memberInfo.getAccessRoles()
                .stream()
                .map(role ->
                        MemberRole.builder()
                                .roleType(role)
                                .member(signUpMember)
                                .build())
                .toList();
        memberRoleRepository.saveAll(memberRoles);
    }

    public LoginInfo login(String email, String password) {
        Member member = getMemberByEmail(email);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new LitararyErrorException(ErrorCode.MISS_MATCH_PASSWORD);
        }

        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        //CustomUserDetailsService 가 실행됨
        Authentication authenticate = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        // 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authenticate);
        member.updateRefreshToken(tokenInfo.getRefreshToken());

        return LoginInfo.builder()
                .tokenInfo(tokenInfo)
                .member(member)
                .build();
    }


    public RefreshTokenInfo refreshToken(String email, String refreshToken) {
        Member member = getMemberByEmail(email);
        //Todo: refreshToken이 일치하는지 체크할 것.
        TokenInfo tokenInfo = jwtTokenProvider.refreshAccessToken(refreshToken);
        member.updateRefreshToken(tokenInfo.getRefreshToken());

        return RefreshTokenInfo.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .accessToken(tokenInfo.getAccessToken())
                .build();
    }

    public void updateAuthCode(long memberId, String accessCode) {
        Member member = getMemberById(memberId);

        member.updateAuthCode(accessCode);
    }

    @Transactional(readOnly = true)
    public void validDuplicatedEmail(String email) {
        boolean isDuplicatedEmail = accountRepository.existsByEmail(email);
        if (isDuplicatedEmail) {
            throw new LitararyErrorException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    @Transactional(readOnly = true)
    public Member findMember(String email) {
        return getMemberByEmail(email);
    }

    public void updatePassword(long memberId, String authCode, String password) {
        Member member = getMemberById(memberId);

        member.validAuthCode(authCode);
        member.updatePasswordEncode(passwordEncoder.encode(password));
    }

    @Transactional(readOnly = true)
    public void checkAuthCode(long memberId, String authCode) {
        Member member = getMemberById(memberId);
        member.isSameAuthCode(authCode);
    }

    private Member getMemberById(long memberId) {
        return accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Member getMemberByEmail(String email) {
        Member member = accountRepository.findByEmail(email)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.ACCOUNT_NOT_FOUND_EMAIL));
        return member;
    }
}
