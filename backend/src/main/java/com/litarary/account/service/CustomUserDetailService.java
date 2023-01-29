package com.litarary.account.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.domain.entity.MemberRole;
import com.litarary.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = accountRepository.findByEmail(email)
                .map(this::createUserDetail)
                .orElseThrow(() -> new RuntimeException("해당 이메일로 가입된 회원이 없습니다."));
        return userDetails;
    }

    private UserDetails createUserDetail(Member member) {
        MemberRole memberRole = member.getMemberRole()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 사용자는 권한을 가지고 있지 않습니다."));

        return User.builder()
                .username(member.getNickName())
                .password(member.getPassword())
                .roles(memberRole.getRoleType().name())
                .build();
    }
}
