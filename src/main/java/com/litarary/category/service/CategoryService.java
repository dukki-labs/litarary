package com.litarary.category.service;

import com.litarary.category.service.dto.MemberCategoryInfo;

public interface CategoryService {
    MemberCategoryInfo findInterestCategory(Long memberId);
}
