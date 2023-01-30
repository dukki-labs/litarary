package com.litarary.account.service;

import com.litarary.account.domain.entity.Interest;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.domain.entity.MemberRole;
import com.litarary.account.repository.AccountRepository;
import com.litarary.account.repository.InterestRepository;
import com.litarary.account.repository.MemberRoleRepository;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.utils.JwtTokenProvider;
import com.litarary.utils.TokenInfo;
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
    private final InterestRepository interestRepository;

    private final MemberRoleRepository memberRoleRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public void signUpMember(SignUpMemberInfo memberInfo) {
        Member member = memberInfo.getMember();
        member.updatePasswordEncode(passwordEncoder.encode(member.getPassword()));
        Member signUpMember = accountRepository.save(member);

        List<Interest> interests = memberInfo.getInterests()
                .stream()
                .map(item ->
                        Interest.builder()
                                .interestType(item)
                                .memberId(signUpMember)
                                .build()
                )
                .toList();
        interestRepository.saveAll(interests);

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

    public TokenInfo login(String email, String password) {

        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        //CustomUserDetailsService 가 실행됨
        Authentication authenticate = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        // 토큰 생성
        return jwtTokenProvider.generateToken(authenticate);
    }


}
