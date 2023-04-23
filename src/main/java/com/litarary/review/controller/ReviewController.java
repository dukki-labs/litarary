package com.litarary.review.controller;

import com.litarary.review.controller.dto.ReviewDto;
import com.litarary.review.service.ReviewService;
import com.litarary.review.service.dto.PageingReviewInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    @GetMapping("/reviews")
    @ResponseStatus(HttpStatus.OK)
    public ReviewDto.Response review(@RequestAttribute("memberId") Long memberId,
                                     @ModelAttribute("request") ReviewDto.Request request) {
        PageingReviewInfo.Request requestDto = reviewMapper.toReviewPageRequest(memberId, request);
        PageingReviewInfo.Response response = reviewService.findBookReviewList(requestDto);

        return ReviewDto.Response
                .builder()
                .size(response.getSize())
                .page(response.getPage())
                .totalPage(response.getTotalPage())
                .totalCount(response.getTotalCount())
                .last(response.isLast())
                .reviewInfoList(response.getReviewInfos())
                .build();
    }

}
