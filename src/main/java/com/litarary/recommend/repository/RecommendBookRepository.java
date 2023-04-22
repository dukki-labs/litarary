package com.litarary.recommend.repository;

import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.entity.Book;
import com.litarary.recommend.domain.entity.RecommendBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendBookRepository extends JpaRepository<RecommendBook, Long> {

    boolean existsByMemberAndBook(Member member, Book book);

    Optional<RecommendBook> findByMemberAndBook(Member member, Book book);
}
