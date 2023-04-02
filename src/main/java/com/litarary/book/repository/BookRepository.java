package com.litarary.book.repository;

import com.litarary.account.domain.entity.Company;
import com.litarary.book.domain.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.company = :company order by b.createdAt desc")
    List<Book> findByRecentBookList(@Param("company") Company company, Pageable pageable);
}
