package com.litarary.account.service;

import com.litarary.account.domain.UseYn;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.domain.entity.MemberRole;
import com.litarary.account.repository.AccountRepository;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmailAndUseYn(email, UseYn.Y)
                .map(this::createUserDetail)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.ACCOUNT_NOT_FOUND_EMAIL));
    }

    private UserDetails createUserDetail(Member member) {
        MemberRole memberRole = member.getMemberRole()
                .stream()
                .findFirst()
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.ACCOUNT_ACCESS_ROLE_MISS_MATCH));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(memberRole.getRoleType().name())
                .build();
    }
}
