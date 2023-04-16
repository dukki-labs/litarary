package com.litarary.book.repository;

import com.litarary.account.domain.entity.Company;
import com.litarary.book.domain.RentalUseYn;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.company = :company order by b.createdAt desc")
    List<Book> findByRecentBookList(@Param("company") Company company, Pageable pageable);

    Optional<Book> findByIdAndRentalUseYnAndCompany(@Param("id") Long id,
                                                    @Param("rentalUseYn") RentalUseYn rentalUseYn,
                                                    @Param("company") Company company);

    @Query("select b, count(br) as borrowCount " +
            "from Book b " +
            "inner join BookRental br " +
            "on b.id = br.book.id " +
            "where b.company = :company " +
            "group by b.id " +
            "order by borrowCount desc")
    List<Book> findBookBorrowBookList(Company company, Pageable pageable);

    @Query("select b from Book b " +
            "where b.company = :company " +
            "and b.category = :category " +
            "order by b.createdAt desc ")
    Page<Book> findByCategoryInBookList(@Param("company") Company company,
                                        @Param("category") Category category,
                                        Pageable pageable);
}
