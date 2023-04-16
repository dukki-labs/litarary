package com.litarary.category.controller;

import com.litarary.category.controller.dto.InterestCategoryDto;
import com.litarary.category.controller.mapper.CategoryMapper;
import com.litarary.category.service.dto.MemberCategoryInfo;
import com.litarary.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    @GetMapping("/interest")
    @ResponseStatus(HttpStatus.OK)
    public InterestCategoryDto.Response interestCategory(@RequestAttribute("memberId") Long memberId) {
        MemberCategoryInfo interestCategory = categoryService.findInterestCategory(memberId);
        return categoryMapper.toInterestCategoryDto(interestCategory);
    }
}
