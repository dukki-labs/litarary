package com.litarary.book.repository;

import com.litarary.book.domain.entity.RentalReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalReviewRepository extends JpaRepository<RentalReview, Long> {

    Page<RentalReview> findByBookIdAndMemberId(Long bookId, Long memberId, PageRequest pageable);

    int countByBookAndMember(Long bookId, Long memberId);
}
