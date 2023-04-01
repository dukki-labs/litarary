package com.litarary.account.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyNameFinderTest {

    @ParameterizedTest(name = "[{index}] {0}의 회사 이름은 {1} 입니다.")
    @CsvSource(value = {"test@te2.st.com:te2", "test@test.com:test", "very.common@example.com:example", "example-indeed@strange-example.com:strange-example"}, delimiter = ':')
    void findCompanyName(String email, String expected) {
        CompanyNameFinder companyNameFinder = new CompanyNameFinder();
        String result = companyNameFinder.findCompanyName(email);

        assertThat(result).isEqualTo(expected);
    }
}