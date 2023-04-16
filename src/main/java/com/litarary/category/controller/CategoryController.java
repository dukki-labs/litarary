package com.litarary.category.controller;

import com.litarary.category.controller.dto.CategoryInBookDto;
import com.litarary.category.controller.dto.InterestCategoryDto;
import com.litarary.category.controller.mapper.CategoryMapper;
import com.litarary.category.service.CategoryService;
import com.litarary.category.service.dto.BookInCategoryInfoRequest;
import com.litarary.category.service.dto.MemberCategoryInfo;
import com.litarary.category.service.dto.PageBookInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/{categoryId}/books")
    @ResponseStatus(HttpStatus.OK)
    public PageBookInfo bookListInCategory(@RequestAttribute("memberId") Long memberId,
                                           @PathVariable Long categoryId,
                                           @Valid @ModelAttribute CategoryInBookDto.Request request) {
        BookInCategoryInfoRequest requestInfo = categoryMapper.toBooksInCategoryRequest(memberId, categoryId, request);
        return categoryService.findCategoryInBooks(requestInfo);
    }
}
