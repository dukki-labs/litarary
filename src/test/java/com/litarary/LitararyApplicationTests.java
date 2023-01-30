package com.litarary;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LitararyApplicationTests {

    @Test
    void contextLoads() {
        String test = "";
        Assertions.assertThat(test).isEmpty();
    }

}
