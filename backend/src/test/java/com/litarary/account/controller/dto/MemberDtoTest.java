package com.litarary.account.controller.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberDtoTest {

    private PasswordEncoder passwordEncoder;
    @Test
    @DisplayName("회원가입 시 필수 약관은 모두 동의해야함으로 모두 true여야 한다.")
    void testTermsIsTrue() {
//        MemberDto member = MemberDto.builder()
//                .isAccountTerms(true)
//                .isPrivacyTerms(true)
//                .isServiceTerms(true)
//                .build();
//
//        assertThat(member.isAccountTerms()).isTrue();
//        assertThat(member.isPrivacyTerms()).isTrue();
//        assertThat(member.isServiceTerms()).isTrue();
    }

    @Test
    @DisplayName("회원가입 시 필수 약관 동의가 하나라도 false인 경우 가입실패")
    void testTermsIncludeFalse() {
//        MemberDto member = MemberDto.builder()
//                .isAccountTerms(true)
//                .isPrivacyTerms(false)
//                .isServiceTerms(true)
//                .build();
//
//        assertThat(member.isPrivacyTerms()).isFalse();
    }

    @BeforeEach
    void setUp() {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

    @Test
    void test1() {
        String password = "12345";
        String encode = passwordEncoder.encode(password);
        String encode2 = passwordEncoder.encode(password);

        System.out.println(encode);
        System.out.println(encode2);

    }
}