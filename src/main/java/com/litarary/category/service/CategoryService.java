package com.litarary.category.service;

import com.litarary.category.service.dto.BookInCategoryInfoRequest;
import com.litarary.category.service.dto.MemberCategoryInfo;
import com.litarary.category.service.dto.PageBookInfo;

public interface CategoryService {
    MemberCategoryInfo findInterestCategory(Long memberId);

    PageBookInfo findCategoryInBooks(BookInCategoryInfoRequest requestInfo);
}
