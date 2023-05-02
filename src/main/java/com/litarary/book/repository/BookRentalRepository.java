package com.litarary.book.repository;

import com.litarary.book.domain.RentalState;
import com.litarary.book.domain.entity.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

    boolean existsByMemberIdAndRentalState(Long memberId, RentalState rentalState);

    Optional<BookRental> findByMemberIdAndRentalState(Long memberId, RentalState rentalState);
}
