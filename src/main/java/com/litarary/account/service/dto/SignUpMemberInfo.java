package com.litarary.account.service.dto;

import com.litarary.account.domain.AccessRole;
import com.litarary.account.domain.BookCategory;
import com.litarary.account.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SignUpMemberInfo {
    private Member member;
    private List<BookCategory> interests;
    private List<AccessRole> accessRoles;
}
