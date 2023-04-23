package com.litarary.review.controller;

import com.litarary.review.controller.dto.ReviewDto;
import com.litarary.review.service.dto.PageingReviewInfo;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public PageingReviewInfo.Request toReviewPageRequest(Long memberId, ReviewDto.Request request) {
        final int minus = 1;
        return PageingReviewInfo.Request
                .builder()
                .memberId(memberId)
                .bookId(request.getBookId())
                .page(request.getPage() - minus)
                .size(request.getSize())
                .build();
    }
}
