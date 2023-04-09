package com.litarary.book.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

    Optional<BookRental> findByMemberIdAndReturnDateTimeIsNull(Long memberId);
}
