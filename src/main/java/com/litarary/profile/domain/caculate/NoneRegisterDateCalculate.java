package com.litarary.profile.domain.caculate;

import com.litarary.profile.domain.RegisterDate;
import com.litarary.profile.domain.RegisterDateCalculate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NoneRegisterDateCalculate implements RegisterDateCalculate {

    @Override
    public LocalDateTime calculateStartDateTime() {
        return LocalDateTime.of(2023, 1, 1, 0, 0);
    }

    @Override
    public boolean matchType(RegisterDate registerDate) {
        return RegisterDate.NONE.equals(registerDate);
    }
}
