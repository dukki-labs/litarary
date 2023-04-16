package com.litarary.category.controller.mapper;

import com.litarary.category.controller.dto.CategoryInBookDto;
import com.litarary.category.controller.dto.InterestCategoryDto;
import com.litarary.category.service.dto.BookInCategoryInfoRequest;
import com.litarary.category.service.dto.MemberCategoryInfo;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public static InterestCategoryDto.Response toInterestCategoryDto(MemberCategoryInfo memberCategoryInfo) {
        return InterestCategoryDto.Response
                .builder()
                .memberId(memberCategoryInfo.getMemberId())
                .memberName(memberCategoryInfo.getNickName())
                .categoryList(memberCategoryInfo.getCategoryList())
                .build();
    }

    public BookInCategoryInfoRequest toBooksInCategoryRequest(Long memberId, Long categoryId, CategoryInBookDto.Request request) {
        return BookInCategoryInfoRequest
                .builder()
                .memberId(memberId)
                .categoryId(categoryId)
                .page(request.getPage() - 1)
                .size(request.getSize())
                .build();
    }
}
