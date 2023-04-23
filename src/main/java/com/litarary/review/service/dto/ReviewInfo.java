package com.litarary.review.service.dto;

import com.litarary.book.domain.entity.RentalReview;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewInfo {
    private long memberId;
    private long bookId;
    private String nickName;
    private String review;
    private LocalDateTime regReviewDateTime;
    private LocalDateTime updateReviewDateTime;

    public static ReviewInfo of(RentalReview rentalReview) {
        return ReviewInfo.builder()
                .memberId(rentalReview.getMember().getId())
                .bookId(rentalReview.getBook().getId())
                .nickName(rentalReview.getMember().getNickName())
                .review(rentalReview.getReview())
                .regReviewDateTime(rentalReview.getCreatedAt())
                .updateReviewDateTime(rentalReview.getUpdatedAt())
                .build();
    }
}
