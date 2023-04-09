package com.litarary.book.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {
}
