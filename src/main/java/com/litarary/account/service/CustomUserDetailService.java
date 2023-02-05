package com.litarary.account.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.domain.entity.MemberRole;
import com.litarary.account.repository.AccountRepository;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.account.AccountErrorException;
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
        return accountRepository.findByEmail(email)
                .map(this::createUserDetail)
                .orElseThrow(() -> new AccountErrorException(ErrorCode.ACCOUNT_NOT_FOUND_EMAIL));
    }

    private UserDetails createUserDetail(Member member) {
        MemberRole memberRole = member.getMemberRole()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AccountErrorException(ErrorCode.ACCOUNT_ACCESS_ROLE_MISS_MATCH));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(memberRole.getRoleType().name())
                .build();
    }
}
