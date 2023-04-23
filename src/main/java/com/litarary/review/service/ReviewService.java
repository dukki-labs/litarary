package com.litarary.review.service;

import com.litarary.review.service.dto.PageingReviewInfo;

public interface ReviewService {
    PageingReviewInfo.Response findBookReviewList(PageingReviewInfo.Request requestDto);
}
