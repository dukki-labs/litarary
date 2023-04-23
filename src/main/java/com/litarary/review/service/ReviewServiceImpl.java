package com.litarary.review.service;

import com.litarary.book.domain.entity.RentalReview;
import com.litarary.book.repository.RentalReviewRepository;
import com.litarary.review.service.dto.PageingReviewInfo;
import com.litarary.review.service.dto.ReviewInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

    private final RentalReviewRepository rentalReviewRepository;

    @Override
    @Transactional(readOnly = true)
    public PageingReviewInfo.Response findBookReviewList(PageingReviewInfo.Request requestDto) {

        final long memberId = requestDto.getMemberId();
        final long bookId = requestDto.getBookId();
        final PageRequest pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        Page<RentalReview> reviews = rentalReviewRepository.findByBookIdAndMemberId(bookId, memberId, pageable);
        List<ReviewInfo> reviewInfos = reviews.getContent()
                .stream()
                .map(ReviewInfo::of)
                .toList();

        return PageingReviewInfo.Response
                .builder()
                .page(requestDto.getPage() + 1)
                .size(requestDto.getSize())
                .totalCount((int) reviews.getTotalElements())
                .totalPage(reviews.getTotalPages())
                .last(reviews.isLast())
                .reviewInfos(reviewInfos)
                .build();
    }

}
