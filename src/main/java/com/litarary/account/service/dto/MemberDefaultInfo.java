package com.litarary.account.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDefaultInfo {
    private long memberId;
    private String email;
}
