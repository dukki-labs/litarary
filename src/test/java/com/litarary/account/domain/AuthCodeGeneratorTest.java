package com.litarary.account.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthCodeGeneratorTest {

    @Test
    void 인증문자의_길이는_무조건_6자리여야한다() {
        String code = AuthCodeGenerator.generateCode();
        assertThat(code.length()).isEqualTo(6);
    }

    @Test
    void 인증문자에는_1개이상의문자와_1개이상의_숫자가들어가야한다() {
        String code = AuthCodeGenerator.generateCode();

        assertThat(code).matches(".*\\d.*");
        assertThat(code).matches("[A-Z].*");
    }
}