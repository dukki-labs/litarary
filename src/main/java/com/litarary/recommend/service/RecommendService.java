package com.litarary.recommend.service;

import com.litarary.recommend.domain.RecommendStatus;

public interface RecommendService {
    int updateRecommendCount(Long memberId, Long bookId, RecommendStatus recommendStatus);
}
