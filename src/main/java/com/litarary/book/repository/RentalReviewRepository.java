package com.litarary.book.repository;

import com.litarary.book.domain.entity.RentalReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalReviewRepository extends JpaRepository<RentalReview, Long> {
}
