package com.litarary.profile.domain;

import java.time.LocalDateTime;

public interface RegisterDateCalculate {

    LocalDateTime calculateStartDateTime();

    boolean matchType(RegisterDate registerDate);
}
