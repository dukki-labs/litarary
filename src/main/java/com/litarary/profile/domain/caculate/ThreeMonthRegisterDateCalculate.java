package com.litarary.profile.domain.caculate;

import com.litarary.profile.domain.RegisterDate;
import com.litarary.profile.domain.RegisterDateCalculate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ThreeMonthRegisterDateCalculate implements RegisterDateCalculate {

    private final long MONTH = 3;

    @Override
    public LocalDateTime calculateStartDateTime() {
        return LocalDateTime.now().minusMonths(MONTH);
    }

    @Override
    public boolean matchType(RegisterDate registerDate) {
        return RegisterDate.THREE_MONTH.equals(registerDate);
    }
}
