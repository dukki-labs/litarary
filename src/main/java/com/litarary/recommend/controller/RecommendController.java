package com.litarary.recommend.controller;

import com.litarary.recommend.controller.dto.RecommendDto;
import com.litarary.recommend.domain.RecommendStatus;
import com.litarary.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/recommend")
@RestController
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping("/books/{bookId}")
    public RecommendDto.Response recommend(@RequestAttribute("memberId") Long memberId,
                                           @PathVariable("bookId") Long bookId,
                                           @RequestParam RecommendStatus recommendStatus) {
        int recommendCount = recommendService.updateRecommendCount(memberId, bookId, recommendStatus);
        return RecommendDto.Response
                .builder()
                .recommendCount(recommendCount)
                .build();
    }
}
