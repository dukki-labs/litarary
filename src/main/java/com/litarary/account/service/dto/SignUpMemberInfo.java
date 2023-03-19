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
    private long memberId;
    private Member member;
    private List<AccessRole> accessRoles;
    private List<BookCategory> bookCategoryList;
}
