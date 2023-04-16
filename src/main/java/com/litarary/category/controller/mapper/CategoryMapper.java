package com.litarary.category.controller.mapper;

import com.litarary.category.controller.dto.InterestCategoryDto;
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
}
