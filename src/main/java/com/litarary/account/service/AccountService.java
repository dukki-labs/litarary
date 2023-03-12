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
        validDuplicatedEmail(memberInfo);

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

    private void validDuplicatedEmail(SignUpMemberInfo memberInfo) {
        boolean isDuplicatedEmail = accountRepository.existsByEmail(memberInfo.getMember().getEmail());
        if (isDuplicatedEmail) {
            throw new LitararyErrorException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    public LoginInfo login(String email, String password) {
        Member member = getMember(email);
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
        Member member = getMember(email);
        //Todo: refreshToken이 일치하는지 체크할 것.
        TokenInfo tokenInfo = jwtTokenProvider.refreshAccessToken(refreshToken);
        member.updateRefreshToken(tokenInfo.getRefreshToken());

        return RefreshTokenInfo.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .accessToken(tokenInfo.getAccessToken())
                .build();
    }

    public void updateAccessCode(long memberId, String accessCode) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateAccessCode(accessCode);
    }

    @Transactional(readOnly = true)
    public Member findMember(String email) {
        return getMember(email);
    }

    public void updatePassword(long memberId, String accessCode, String password) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        member.validAccessCode(accessCode);
        member.updatePasswordEncode(passwordEncoder.encode(password));
    }

    private Member getMember(String email) {
        Member member = accountRepository.findByEmail(email)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.ACCOUNT_NOT_FOUND_EMAIL));
        return member;
    }
}
