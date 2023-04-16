package com.litarary.category.service.dto;

import com.litarary.book.domain.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberCategoryInfo {
    private long memberId;
    private String nickName;
    private List<Category> categoryList;
}
