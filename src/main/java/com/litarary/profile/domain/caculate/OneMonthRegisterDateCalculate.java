package com.litarary.profile.domain.caculate;

import com.litarary.profile.domain.RegisterDate;
import com.litarary.profile.domain.RegisterDateCalculate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OneMonthRegisterDateCalculate implements RegisterDateCalculate {

    private final long MONTH = 1;

    @Override
    public LocalDateTime calculateStartDateTime() {
        return LocalDateTime.now().minusMonths(MONTH);
    }

    @Override
    public boolean matchType(RegisterDate registerDate) {
        return RegisterDate.ONE_MONTH.equals(registerDate);
    }
}
